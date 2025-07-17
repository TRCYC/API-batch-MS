package com.rcyc.batchsystem.reader;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rcyc.batchsystem.model.elastic.Transfer;
import com.rcyc.batchsystem.model.elastic.TransferItem;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.model.resco.Availability;
import com.rcyc.batchsystem.model.resco.Event;
import com.rcyc.batchsystem.model.resco.EventDetail;
import com.rcyc.batchsystem.model.resco.Facility;
import com.rcyc.batchsystem.model.resco.Location;
import com.rcyc.batchsystem.model.resco.ReqListEvent;
import com.rcyc.batchsystem.model.resco.ReqListItem;
import com.rcyc.batchsystem.model.resco.ResListEvent;
import com.rcyc.batchsystem.model.resco.ResListItem;
import com.rcyc.batchsystem.model.resco.ResListLocation;
import com.rcyc.batchsystem.model.resco.User;
import com.rcyc.batchsystem.service.AuditService;
import com.rcyc.batchsystem.service.RescoClient;
import com.rcyc.batchsystem.service.ScheduledJobService;

@Component
public class TransferReader implements ItemReader<DefaultPayLoad<Transfer, Object, Transfer>> {

	@Autowired
	private RescoClient rescoClient;
	private boolean alreadyRead = false;
	private int availUnits = 0;
	private final Long jobId;
    private AuditService auditService;
    private LocalDateTime today = LocalDateTime.now();
    private ScheduledJobService scheduledJobService;
	
	public TransferReader(RescoClient rescoClient, Long jobId,AuditService auditService,ScheduledJobService scheduledJobService) {
        this.rescoClient = rescoClient;
        this.jobId = jobId;
        this.auditService =auditService;
        this.scheduledJobService = scheduledJobService;
    }

	class EventProcessorTask extends RecursiveTask<Map<String, Object>> {
		private static final int THRESHOLD = 200;
		private List<EventDetail> events;
		private Map<String, Location> portMap;
		private String[] transferTypeArr;

		EventProcessorTask(List<EventDetail> events, Map<String, Location> portMap, String[] transferTypeArr) {
			this.events = events;
			this.portMap = portMap;
			this.transferTypeArr = transferTypeArr;
		}

		@Override
		protected Map<String, Object> compute() {
			if (events.size() <= THRESHOLD) {
				return process(events);
			} else {
				int mid = events.size() / 2;
				EventProcessorTask left = new EventProcessorTask(events.subList(0, mid), portMap, transferTypeArr);
				EventProcessorTask right = new EventProcessorTask(events.subList(mid, events.size()), portMap,
						transferTypeArr);
				invokeAll(left, right);
				Map<String, Object> result = new HashMap<>();
				result.putAll(left.join());
				result.putAll(right.join());
				return result;
			}
		}

		private Map<String, Object> process(List<EventDetail> eventList) {
			Map<String, Object> localMap = new HashMap<>();
			for (EventDetail event : eventList) {
				TransferItem transferItem = new TransferItem();
				String transferTfResultStatus = "";
				String portCode = event.getBegLocation();
				int voyageId = event.getEventId();
				String voyageCode = event.getCode();
				System.out.println("VoyageCode-" + voyageCode + " ::VoyageId-" + voyageId);

				Location location = portMap.get(portCode);
				if (location != null) {
					transferItem.setPortName(location.getName());
					transferItem.setCountryCode(location.getCode());
				} else {
					transferItem.setPortName("");
					transferItem.setCountryCode("");
				}

				transferItem.setVoyageId(voyageId);
				transferItem.setPortCode(portCode);
				transferItem.setVoyageCode(voyageCode);

				ResListItem reslistItemForVoyage = getTransfersByVoyage(transferTypeArr, voyageId,
						transferTfResultStatus);
				transferItem.setTransferTfResultStatus(transferTfResultStatus);

				if (reslistItemForVoyage != null && reslistItemForVoyage.getItemList() != null
						&& reslistItemForVoyage.getItemList().getItemList() != null) {
					transferItem.setItemList(reslistItemForVoyage.getItemList().getItemList());
				} else {
					transferItem.setItemList(new ArrayList<>());
				}

				localMap.put(voyageCode, transferItem);
			}
			return localMap;
		}

	}

	@Override
	public DefaultPayLoad<Transfer, Object, Transfer> read() {
		boolean flag = scheduledJobService.isJobAvailableForExecution(jobId, auditService);
        if (!flag){
            return null;
        }
		DefaultPayLoad<Transfer, Object, Transfer> transferPayLoad = new DefaultPayLoad<>();
		auditService.logAudit(jobId, "feed_type", today, today, today,"Resco call initiated");
		try {
			//if (alreadyRead)
			//	return null;
			transferPayLoad.setReader(getTransfersFromResco());
			alreadyRead = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return transferPayLoad;
	}

	private Map<String, Object> getTransfersFromResco() {
		List<EventDetail> eventList = new ArrayList<EventDetail>();
		String[] transferTypeArr = { "BU", "XI", "TF" };
		Map<String, Object> transferReaderMap = new HashMap<String, Object>();
		try {

			ResListEvent voyageList = rescoClient.getAllVoyages(0);
			ResListEvent hotelList = getHotelsFromResco();
			if (voyageList.getEventList() != null && hotelList.getEventList() != null) {
				System.out.println("Voyage Size--" + voyageList.getEventList().size());
				System.out.println("Hotel Size--" + hotelList.getEventList().size());
			}
			eventList.addAll(voyageList.getEventList());
			eventList.addAll(hotelList.getEventList());
			System.out.println("Total Event Size--" + eventList.size());
			//if (eventList.stream().filter(obj -> obj.getEventId() == 897).findFirst().isPresent())
			// eventList = new ArrayList<>(eventList.subList(0, 50));
			// eventList = eventList.stream().filter(obj-> obj.getEventId()==897).toList();
			System.out.println("Total Event Size after split--" + eventList.size());
			ResListLocation portList = rescoClient.getAllPorts("P");
			Map<String, Location> portmap = portList.getLocationList().getLocations().stream()
					.collect(Collectors.toMap(Location::getCode, Function.identity()));

			ForkJoinPool pool = new ForkJoinPool();
			EventProcessorTask task = new EventProcessorTask(eventList, portmap, transferTypeArr);
			transferReaderMap = pool.invoke(task);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transferReaderMap;
	}

	private ResListEvent getHotelsFromResco() {
		ReqListEvent req = new ReqListEvent();
		req.setUser(new User("webapiprod1", "theGr8tw1de0pen#305"));
		Facility facility = new Facility();
		facility.setType("H");
		req.setFacility(facility);
		Event event = new Event();
		event.setDisabled(1);
		req.setEvent(event);
		Availability availability = new Availability(availUnits);
		req.setAvailability(availability);
		return rescoClient.getHotels(req);
	}

	private ResListItem getTransfersByVoyage(String[] typeArr, int voyageId, String transferTfResultStatus) {
		ReqListItem req = new ReqListItem();
		req.setUser(new User("webapiprod1", "theGr8tw1de0pen#305"));
		return rescoClient.getTransferArr(typeArr, voyageId, transferTfResultStatus);
	}
}

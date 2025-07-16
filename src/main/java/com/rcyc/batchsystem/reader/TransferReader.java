package com.rcyc.batchsystem.reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rcyc.batchsystem.model.elastic.Port;
import com.rcyc.batchsystem.model.elastic.Transfer;
import com.rcyc.batchsystem.model.elastic.TransferItem;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.model.resco.Availability;
import com.rcyc.batchsystem.model.resco.Event;
import com.rcyc.batchsystem.model.resco.EventDetail;
import com.rcyc.batchsystem.model.resco.Facility;
import com.rcyc.batchsystem.model.resco.Item;
import com.rcyc.batchsystem.model.resco.Location;
import com.rcyc.batchsystem.model.resco.ReqListEvent;
import com.rcyc.batchsystem.model.resco.ReqListItem;
import com.rcyc.batchsystem.model.resco.ResListEvent;
import com.rcyc.batchsystem.model.resco.ResListItem;
import com.rcyc.batchsystem.model.resco.ResListLocation;
import com.rcyc.batchsystem.model.resco.User;
import com.rcyc.batchsystem.service.RescoClient;

@Component
public class TransferReader implements ItemReader<DefaultPayLoad<Transfer, Object, Transfer>> {

	@Autowired
	private RescoClient rescoClient;
	private boolean alreadyRead = false;
	private int availUnits = 0;

	@Override
	public DefaultPayLoad<Transfer, Object, Transfer> read() {
		DefaultPayLoad<Transfer, Object, Transfer> transferPayLoad = new DefaultPayLoad<>();
		try {
			if (alreadyRead)
				return null;
			transferPayLoad.setReader(getTransfersFromResco());
			alreadyRead = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return transferPayLoad;
	}

	private Map<String, Object> getTransfersFromResco() {
		// ResListEvent eventList = new ResListEvent();
		List<EventDetail> eventList = new ArrayList<EventDetail>();
		// ResListItem transferItemList = new ResListItem();
		// List<TransferItem> transferReaderList = new ArrayList<>();
		String[] transferTypeArr = { "BU", "XI", "TF" };
		Map<String, Object> transferReaderMap = new HashMap<String, Object>();
		try {

			ResListEvent voyageList = rescoClient.getAllVoyages();
			ResListEvent hotelList = getHotelsFromResco();
			if (voyageList.getEventList() != null && hotelList.getEventList() != null) {
				System.out.println("Voyage Size--" + voyageList.getEventList().size());
				System.out.println("Hotel Size--" + hotelList.getEventList().size());
			}
			eventList.addAll(voyageList.getEventList());
			eventList.addAll(hotelList.getEventList());
			System.out.println("Total Event Size--" + eventList.size());
			// eventList = new ArrayList<>(eventList.subList(0, 50));
			// eventList = eventList.stream().filter(obj-> obj.getEventId()==2433).toList();
			System.out.println("Total Event Size after split--" + eventList.size());
			ResListLocation portList = rescoClient.getAllPorts("P");
			Map<String, Location> portmap = portList.getLocationList().getLocations().stream().collect(Collectors.toMap(Location::getCode, Function.identity()));

			for (EventDetail event : eventList) {
				TransferItem transferItem = new TransferItem();
				String transferTfResultStatus = "";
				// List<Item> itemList = new ArrayList<Item>();
				String portCode = event.getBegLocation();
				int voyageId = event.getEventId();
				String voyageCode = event.getCode();
				/*String countryCode = "";
				String portName = "";

				Optional<Location> location = portList.getLocationList().getLocations().stream()
						.filter(obj -> obj.getCode().equals(portCode)).findFirst();
				
				if (location.isPresent()) {
					countryCode = location.get().getCode();
					portName = location.get().getName();
				}*/
				Location location = portmap.get(portCode);
				if (location != null) {
					transferItem.setPortName(location.getName());
					transferItem.setCountryCode(location.getCode());
				} else {
					System.out.println("portCode not found--"+portCode);
					transferItem.setPortName("");
					transferItem.setCountryCode("");
				}
				transferItem.setVoyageId(voyageId);
				transferItem.setPortCode(portCode);
				transferItem.setVoyageCode(voyageCode);

				ResListItem reslistItemForVoyage = getTransfersByVoyage(transferTypeArr, voyageId, transferTfResultStatus);
				transferItem.setTransferTfResultStatus(transferTfResultStatus);
				
				/*ResListItem reslistItemBU = getTransfersByVoyage("BU", voyageId);// TRANSFER_BUS_GROUP_TYPE
				ResListItem reslistItemXI = getTransfersByVoyage("XI", voyageId);// TRANSFER_TAXI_GROUP_TYPE
				ResListItem reslistItemTF = getTransfersByVoyage("TF", voyageId);// TF

				if (reslistItemBU != null && reslistItemBU.getItemList() != null
						&& reslistItemBU.getItemList().getItemList() != null) {
					System.out.println("Transfers BU size - " + reslistItemBU.getItemList().getItemList().size());
					itemList.addAll(reslistItemBU.getItemList().getItemList());
				}
				if (reslistItemXI != null && reslistItemXI.getItemList() != null
						&& reslistItemXI.getItemList().getItemList() != null) {
					System.out.println("Transfers XI size - " + reslistItemXI.getItemList().getItemList().size());
					itemList.addAll(reslistItemXI.getItemList().getItemList());
				}
				if (reslistItemTF != null) {
					transferItem.setTransferTfResultStatus(reslistItemTF.getResult().getStatus());

					if (reslistItemTF.getItemList() != null && reslistItemTF.getItemList().getItemList() != null) {
						System.out.println("Transfers TF size" + reslistItemTF.getItemList().getItemList().size());
						itemList.addAll(reslistItemTF.getItemList().getItemList());
					}
				}*/
				int size = 0;
				if(reslistItemForVoyage!=null && reslistItemForVoyage.getItemList()!=null && reslistItemForVoyage.getItemList().getItemList()!=null){
					transferItem.setItemList(reslistItemForVoyage.getItemList().getItemList());
					size = reslistItemForVoyage.getItemList().getItemList().size();
				} else {
					transferItem.setItemList(new ArrayList<Item>());
				}
				System.out.println(
						"VoyageCode-" + voyageCode + " ::VoyageId-" + voyageId + " ::TransferList Size--" + size);
				// transferReaderList.add(transferItem);
				//transferItem.setItemList(itemList);
				// System.out.println("transferItem --"+transferItem.toString());
				transferReaderMap.put(voyageCode, transferItem);
			}
			System.out.println("From Reader >> " + transferReaderMap);
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

	private ResListItem getTransfersByVoyage(String type, int voyageId) {
		//System.out.println("Type--" + type + "   voyageId--" + voyageId);
		ReqListItem req = new ReqListItem();
		req.setUser(new User("webapiprod1", "theGr8tw1de0pen#305"));
		return rescoClient.getTransfer(type, voyageId);
	}
	
	private ResListItem getTransfersByVoyage(String[] typeArr, int voyageId, String transferTfResultStatus) {
		//System.out.println("Type--" + type + "   voyageId--" + voyageId);
		ReqListItem req = new ReqListItem();
		req.setUser(new User("webapiprod1", "theGr8tw1de0pen#305"));
		return rescoClient.getTransferArr(typeArr, voyageId, transferTfResultStatus);
	}
}

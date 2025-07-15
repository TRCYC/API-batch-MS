package com.rcyc.batchsystem.reader;

import java.util.List;
import java.util.Optional;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rcyc.batchsystem.model.elastic.Port;
import com.rcyc.batchsystem.model.elastic.Transfer;
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
			transferPayLoad.setReader(getTransferFromResco());
			alreadyRead = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return transferPayLoad;
	}

	private ResListItem getTransferFromResco() {
		//ResListLocation listLocation = new ResListLocation();
		ResListEvent eventList = new ResListEvent();
		ResListItem transferItemList = new ResListItem();
		try {

			ResListEvent voyageList = rescoClient.getAllVoyages();
			ResListEvent hotelList = getHotelsFromResco();
			eventList.getEventList().addAll(voyageList.getEventList());
			eventList.getEventList().addAll(hotelList.getEventList());
			ResListLocation portList = rescoClient.getAllPorts("P");

			for (EventDetail event : eventList.getEventList()) {
				String portCode = event.getBegLocation();
				String voyageId = event.getCode();
				String countryCode = "";
				String portName = "";

				Optional<Location> location = portList.getLocationList().getLocations().stream()
						.filter(obj -> obj.getCode().equals(portCode)).findFirst();
				if (location.isPresent()) {
					countryCode = location.get().getCode();
					portName = location.get().getName();
				}

				ResListItem reslistItemBU = getTransfersFromResco("BU", voyageId);// TRANSFER_BUS_GROUP_TYPE
				ResListItem reslistItemXI = getTransfersFromResco("XI", voyageId);// TRANSFER_TAXI_GROUP_TYPE
				ResListItem reslistItemTF = getTransfersFromResco("TF", voyageId);// TF

				transferItemList.getItemList().getItemList().addAll(reslistItemBU.getItemList().getItemList());
				transferItemList.getItemList().getItemList().addAll(reslistItemXI.getItemList().getItemList());
				transferItemList.getItemList().getItemList().addAll(reslistItemTF.getItemList().getItemList());
			}

			System.out.println("From Reader >> " + transferItemList.getItemList().getItemList().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transferItemList;
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

	private ResListItem getTransfersFromResco(String type, String voyageId) {
		ReqListItem req = new ReqListItem();
		req.setUser(new User("webapiprod1", "theGr8tw1de0pen#305"));
		return rescoClient.getTransfer(type, voyageId);
	}
}

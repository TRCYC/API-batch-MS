package com.rcyc.batchsystem.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rcyc.batchsystem.model.elastic.Port;
import com.rcyc.batchsystem.model.elastic.Transfer;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.model.resco.Availability;
import com.rcyc.batchsystem.model.resco.Event;
import com.rcyc.batchsystem.model.resco.Facility;
import com.rcyc.batchsystem.model.resco.ReqListEvent;
import com.rcyc.batchsystem.model.resco.ReqListItem;
import com.rcyc.batchsystem.model.resco.ResListEvent;
import com.rcyc.batchsystem.model.resco.ResListItem;
import com.rcyc.batchsystem.model.resco.ResListLocation;
import com.rcyc.batchsystem.model.resco.User;
import com.rcyc.batchsystem.service.RescoClient;

@Component
public class TransferReader implements ItemReader<DefaultPayLoad<Transfer, Object, Transfer>>{

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
    
    private ResListLocation getTransferFromResco() {
        ResListLocation listLocation = new ResListLocation();
        try {
        	
        	ResListEvent voyageList = rescoClient.getAllVoyages();
        	ResListEvent hotelList = getHotelsFromResco();
        	ResListLocation portList = rescoClient.getAllPorts("P");
        	
        	ResListItem reslistItemBU = getTransfersFromResco("BU","voyageId");//TRANSFER_BUS_GROUP_TYPE
        	ResListItem reslistItemXI = getTransfersFromResco("XI","voyageId");//TRANSFER_TAXI_GROUP_TYPE
        	ResListItem reslistItemTF = getTransfersFromResco("TF","voyageId");//TF
        	
            //ResListLocation pList = rescoClient.getAllPorts("P");
            //ResListLocation oList = rescoClient.getAllPorts("O");
            //listLocation.getLocationList().getLocations().addAll(pList.getLocationList().getLocations());
            //listLocation.getLocationList().getLocations().addAll(oList.getLocationList().getLocations());
            
            System.out.println("From Reader >> "+listLocation.getLocationList().getLocations().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listLocation;
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

package com.rcyc.batchsystem.service;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rcyc.batchsystem.model.resco.Agency;
import com.rcyc.batchsystem.model.resco.Availability;
import com.rcyc.batchsystem.model.resco.Dictionary;
import com.rcyc.batchsystem.model.resco.Event;
import com.rcyc.batchsystem.model.resco.Facility;
import com.rcyc.batchsystem.model.resco.Item;
import com.rcyc.batchsystem.model.resco.Itinerary;
import com.rcyc.batchsystem.model.resco.Location;
import com.rcyc.batchsystem.model.resco.ReqListDictionary;
import com.rcyc.batchsystem.model.resco.ReqListEvent;
import com.rcyc.batchsystem.model.resco.ReqListItem;
import com.rcyc.batchsystem.model.resco.ReqListItinerary;
import com.rcyc.batchsystem.model.resco.ReqListLocation;
import com.rcyc.batchsystem.model.resco.ResListDictionary;
import com.rcyc.batchsystem.model.resco.ResListEvent;
import com.rcyc.batchsystem.model.resco.ResListItem;
import com.rcyc.batchsystem.model.resco.ResListItenarary;
import com.rcyc.batchsystem.model.resco.ResListItinerary;
import com.rcyc.batchsystem.model.resco.ResListLocation;
import com.rcyc.batchsystem.model.resco.User;

@Service
public class RescoClient {

    @Autowired
    private RestTemplate restTemplate;

    public ResListDictionary getAllRegions(){ 
        Dictionary dictionary = new Dictionary("RGN", 0);
        ReqListDictionary req = new ReqListDictionary(getUser(), dictionary);
        ResListDictionary response =  restTemplate.postForObject("https://stgwebapi.ritz-carltonyachtcollection.com/rescoweb/ResWebConvert/InterfaceResco.aspx", req, ResListDictionary.class);
       System.out.println(response.toString());
        return response;
    }

    public ResListLocation getAllPorts(String type){
        ReqListLocation reqListLocation = new ReqListLocation(getUser(),new Location(0,type));
        ResListLocation response =  restTemplate.postForObject("https://stgwebapi.ritz-carltonyachtcollection.com/rescoweb/ResWebConvert/InterfaceResco.aspx", reqListLocation, ResListLocation.class);
        // System.out.println(response.toString());
        return response;
    }

    public ResListEvent getAllVoyages(){
    	System.out.println("Inside getAllVoyages");
        ReqListEvent reqListEvent = new ReqListEvent();
        reqListEvent.setUser(getUser());
        reqListEvent.setAvailability(new Availability(0));
        reqListEvent.setEvent(new Event(0));
        reqListEvent.setFacility(new Facility("O"));
        ResListEvent resListEvent= restTemplate.postForObject("https://stgwebapi.ritz-carltonyachtcollection.com/rescoweb/ResWebConvert/InterfaceResco.aspx", reqListEvent, ResListEvent.class);
        return resListEvent;
    }

    public ResListEvent getHotels(ReqListEvent reqListEvent) {
        System.out.println("Resco calling For Hotel");
        ResListEvent resListEvent = restTemplate.postForObject(
            "https://stgwebapi.ritz-carltonyachtcollection.com/rescoweb/ResWebConvert/InterfaceResco.aspx",
            reqListEvent,
            ResListEvent.class
        );
        System.out.println("Resco response completed >> "+resListEvent.getEventList().size());
        return resListEvent;
    }

    public ResListItinerary getAllItineraryByEvent(Long eventId){ 
        ReqListItinerary reqListItinerary = new ReqListItinerary();
        reqListItinerary.setUser(getUser());
        reqListItinerary.setItinerary(new Itinerary(eventId));
        ResListItinerary response =  restTemplate.postForObject("https://stgwebapi.ritz-carltonyachtcollection.com/rescoweb/ResWebConvert/InterfaceResco.aspx", reqListItinerary, ResListItinerary.class);
         System.out.println(response.toString());
        return response;
    }
    
	public ResListItem getTransfer(String type, int voyageId) {
		ReqListItem reqListItem = new ReqListItem();
		reqListItem.setUser(getUser());
		reqListItem.setAgency(new Agency("40622")); // TRANSFER_AGENT_ID
		Item item = new Item();
		item.setGroupType(type);
		reqListItem.setItem(item);
		reqListItem.setEvent(new Event(String.valueOf(voyageId)));
		ResListItem response = new ResListItem();
		try {
			// convertToXml(reqListItem);
			response = restTemplate.postForObject(
					"https://stgwebapi.ritz-carltonyachtcollection.com/rescoweb/ResWebConvert/InterfaceResco.aspx",
					reqListItem, ResListItem.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*if (response.getItemList() != null) {
			if (response.getItemList().getItemList() != null)
				System.out.println(response.getItemList().getItemList().size());
		} else
			System.out.println("response getItemList is null");*/
		return response;
	}
	
	public ResListItem getTransferArr(String[] typeArr, int voyageId, String transferTfResultStatus) {
		ReqListItem reqListItem = new ReqListItem();
		reqListItem.setUser(getUser());
		reqListItem.setAgency(new Agency("40622")); // TRANSFER_AGENT_ID
		//Item item = new Item();
		//item.setGroupType(type);
		//reqListItem.setItem(item);
		reqListItem.setEvent(new Event(String.valueOf(voyageId)));
		ResListItem response = new ResListItem();
		List<Item> itemList = new ArrayList<Item>();
		for(String type: typeArr) {
			Item item = new Item();
			item.setGroupType(type);
			reqListItem.setItem(item);
			try {
				// convertToXml(reqListItem);
				response = restTemplate.postForObject(
						"https://stgwebapi.ritz-carltonyachtcollection.com/rescoweb/ResWebConvert/InterfaceResco.aspx",
						reqListItem, ResListItem.class);

				if (response != null) {
					if (type.equals("TF"))
						transferTfResultStatus = response.getResult().getStatus();
					if (response.getItemList() != null && response.getItemList().getItemList() != null) {
						System.out.println(
								"Transfer size - " + type + " -" + response.getItemList().getItemList().size());
						itemList.addAll(response.getItemList().getItemList());
					}
				}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		response.getItemList().setItemList(itemList);
		/*if (response.getItemList() != null) {
			if (response.getItemList().getItemList() != null)
				System.out.println(response.getItemList().getItemList().size());
		} else
			System.out.println("response getItemList is null");*/
		return response;
	}

    private User getUser(){
        return new User("webapiprod1","theGr8tw1de0pen#305");
    }
    
	// To print your object in XML
	public void convertToXml(Object obj) {
		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			marshaller.marshal(obj, sw);
			System.out.println(sw.toString());
			// return sw.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
			// return null;
		}
	}
}

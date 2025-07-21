package com.rcyc.batchsystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rcyc.batchsystem.model.resco.Agency;
import com.rcyc.batchsystem.model.resco.Availability;
import com.rcyc.batchsystem.model.resco.Category;
import com.rcyc.batchsystem.model.resco.Dictionary;
import com.rcyc.batchsystem.model.resco.Event;
import com.rcyc.batchsystem.model.resco.Facility;
import com.rcyc.batchsystem.model.resco.Item;
import com.rcyc.batchsystem.model.resco.Itinerary;
import com.rcyc.batchsystem.model.resco.Location;
import com.rcyc.batchsystem.model.resco.Rate;
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
import com.rcyc.batchsystem.model.resco.ReqListCategory;
import com.rcyc.batchsystem.model.resco.ResListCategory;

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
         System.out.println(response.toString());
        return response;
    }

    public ResListEvent getAllVoyages(int disabled){
        ReqListEvent reqListEvent = new ReqListEvent();
        reqListEvent.setUser(getUser());
        reqListEvent.setAvailability(new Availability(0));
        reqListEvent.setEvent(new Event(disabled));
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

    public ResListItinerary getAllItinerariesByFacility(Long facilityId){ 
        ReqListItinerary reqListItinerary = new ReqListItinerary();
        reqListItinerary.setUser(getUser());
        Itinerary itinerary = new Itinerary();
        itinerary.setFacilityId(facilityId);
        reqListItinerary.setItinerary(itinerary);
        ResListItinerary response =  restTemplate.postForObject("https://stgwebapi.ritz-carltonyachtcollection.com/rescoweb/ResWebConvert/InterfaceResco.aspx", reqListItinerary, ResListItinerary.class);
         System.out.println(response.toString());
        return response;
    }

    public ResListCategory getSuiteByCurrency(String currencyType, String eventId, int surcharges) {
        System.out.println("Currency >> "+currencyType + " Event >>"+eventId);
        ReqListCategory req = new ReqListCategory();
        Agency agency = new Agency();
        agency.setAgentId("40622"); // Replace with actual agent id or inject as needed
        Category category = new Category();
        category.setEventId(eventId);
        Rate rate = new Rate();
        rate.setCurrency(currencyType);
        rate.setSurcharges(surcharges);
        req.setUser(getUser());
        req.setAgency(agency);
        req.setCategory(category);
        req.setRate(rate);
 
        return restTemplate.postForObject(
            "https://stgwebapi.ritz-carltonyachtcollection.com/rescoweb/ResWebConvert/InterfaceResco.aspx",
            req,
            ResListCategory.class
        );
    }

    public ResListItem getTransferArr(String[] typeArr, int voyageId, String transferTfResultStatus) {
		ReqListItem reqListItem = new ReqListItem();
		reqListItem.setUser(getUser());
		Agency agency = new Agency();
        agency.setAgentId("40622"); // TRANSFER_AGENT_ID
		reqListItem.setAgency(agency);
		reqListItem.setEvent(new Event(String.valueOf(voyageId)));
		ResListItem response = new ResListItem();
		List<Item> itemList = new ArrayList<Item>();
		for(String type: typeArr) {
			Item item = new Item();
			item.setGroupType(type);
			reqListItem.setItem(item);
			try {
				// convertToXml(reqListItem);
				RestTemplate rt = new RestTemplate();
				restTemplate.getMessageConverters().add(new Jaxb2RootElementHttpMessageConverter());
				response = rt.postForObject(
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
}

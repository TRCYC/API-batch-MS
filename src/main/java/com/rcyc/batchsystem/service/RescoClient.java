package com.rcyc.batchsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rcyc.batchsystem.model.resco.Availability;
import com.rcyc.batchsystem.model.resco.Dictionary;
import com.rcyc.batchsystem.model.resco.Event;
import com.rcyc.batchsystem.model.resco.Facility;
import com.rcyc.batchsystem.model.resco.Itinerary;
import com.rcyc.batchsystem.model.resco.Location;
import com.rcyc.batchsystem.model.resco.ReqListDictionary;
import com.rcyc.batchsystem.model.resco.ReqListEvent;
import com.rcyc.batchsystem.model.resco.ReqListItinerary;
import com.rcyc.batchsystem.model.resco.ReqListLocation;
import com.rcyc.batchsystem.model.resco.ResListDictionary;
import com.rcyc.batchsystem.model.resco.ResListEvent;
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
         System.out.println(response.toString());
        return response;
    }

    public ResListEvent getAllVoyages(){
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

    private User getUser(){
        return new User("webapiprod1","theGr8tw1de0pen#305");
    }
}

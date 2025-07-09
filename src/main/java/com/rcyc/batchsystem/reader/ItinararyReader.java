package com.rcyc.batchsystem.reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.entity.FeedDateRangeEntity;
import com.rcyc.batchsystem.model.elastic.Itinerary; 
import com.rcyc.batchsystem.model.resco.*;
import com.rcyc.batchsystem.model.resco.Location;
import com.rcyc.batchsystem.model.resco.ResListEvent;
import com.rcyc.batchsystem.model.resco.ResListItenarary;
import com.rcyc.batchsystem.model.resco.ResListLocation;
import com.rcyc.batchsystem.repository.FeedDateRangeRepository;
import com.rcyc.batchsystem.service.RescoClient;

@Component
public class ItinararyReader implements ItemReader<DefaultPayLoad<Itinerary, Object, Itinerary>> {

    @Autowired
    private RescoClient rescoClient;
    @Autowired
    private FeedDateRangeRepository feedDateRangeRepository;
    private boolean alreadyRead = false;

    @Override
    public DefaultPayLoad<Itinerary, Object, Itinerary> read()
            throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        try {
            if (alreadyRead)
                return null;
            DefaultPayLoad<Itinerary, Object, Itinerary> itenararyPayLoad = new DefaultPayLoad<>();
            itenararyPayLoad.setReader(getItenararyFromResco());
            alreadyRead = true;
            return itenararyPayLoad;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<String,Object> getItenararyFromResco() {  
        List<FeedDateRangeEntity> dateRanges = feedDateRangeRepository.findByType("ITR")   ;  
        ResListLocation pLocations =  rescoClient.getAllPorts("P");
        ResListLocation oLocations =  rescoClient.getAllPorts("O");
        
        ResListEvent resListEvent = rescoClient.getAllVoyages();

        List<com.rcyc.batchsystem.model.resco.Itinerary> itineraries = getItinerariesByVoyages(resListEvent.getEventList(),dateRanges.get(0));
        Map<String,Object> itineraryMap =new HashMap<>();

        itineraryMap.put("P_TYPE_PORTS", pLocations.getLocationList().getLocations());
        itineraryMap.put("O_TYPE_PORTS", oLocations.getLocationList().getLocations());
        itineraryMap.put("VOYAGES", resListEvent.getEventList());
        itineraryMap.put("ITINERARIES", itineraries);

        return  itineraryMap;
    }

    private List<com.rcyc.batchsystem.model.resco.Itinerary> getItinerariesByVoyages(List<EventDetail> eventList,FeedDateRangeEntity dateRangeEntity) {
        List<com.rcyc.batchsystem.model.resco.Itinerary> rescoItineraryList = new ArrayList<>();
        int counter =0;
        for(EventDetail eventDetail : eventList){
            System.out.println(eventDetail.getEventId());
            if(eventDetail!=null && eventDetail.getBegDate()!=null && dateRangeEntity.isBegDateOnOrAfterStartAt(eventDetail.getBegDate())){
                ResListItinerary listItinerary = rescoClient.getAllItineraryByEvent(Long.valueOf(eventDetail.getEventId()));
                rescoItineraryList.addAll(listItinerary.getItineraryList());
                System.out.println(counter++);
            }
        }
        return rescoItineraryList;
          
    }

    
} 
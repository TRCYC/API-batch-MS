package com.rcyc.batchsystem.reader;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rcyc.batchsystem.entity.FeedDateRangeEntity;
import com.rcyc.batchsystem.model.elastic.Voyage;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.model.resco.EventDetail;
import com.rcyc.batchsystem.model.resco.ResListDictionary;
import com.rcyc.batchsystem.model.resco.ResListEvent;
import com.rcyc.batchsystem.model.resco.ResListItinerary;
import com.rcyc.batchsystem.model.resco.ResListLocation;
import com.rcyc.batchsystem.repository.FeedDateRangeRepository;
import com.rcyc.batchsystem.service.RescoClient;
import java.util.ArrayList;

@Component
public class VoyageReader implements ItemReader<DefaultPayLoad<Voyage, Object, Voyage>> {
    private boolean alreadyRead = false;

    @Autowired
    private RescoClient rescoClient;
    @Autowired
    private FeedDateRangeRepository feedDateRangeRepository;

    @Override
    public DefaultPayLoad<Voyage, Object, Voyage> read() {
        DefaultPayLoad<Voyage, Object, Voyage> voyagePayLoad = new DefaultPayLoad<>();
        try {
            if (alreadyRead)
                return null;
            List<FeedDateRangeEntity> dateRanges = feedDateRangeRepository.findByType("VOY");
            ResListDictionary listDictionary = rescoClient.getAllRegions();
            ResListLocation pList = rescoClient.getAllPorts("P");
            ResListLocation oList = rescoClient.getAllPorts("O");
            ResListItinerary evrimaItinerary = rescoClient.getAllItinerariesByFacility(12l);
            ResListItinerary ilmaItinerary = rescoClient.getAllItinerariesByFacility(13l);
            ResListItinerary luminaraItinerary = rescoClient.getAllItinerariesByFacility(93l);
            

            ResListEvent resListEvent = rescoClient.getAllVoyages(1);

            List<EventDetail> eventList = resListEvent.getEventList();




            FeedDateRangeEntity dateRangeEntity = dateRanges.get(0);
            List<Voyage> voyages = eventList.stream()
                .filter(event -> dateRangeEntity.isBegDateOnOrAfterStartAt(event.getBegDate()))
                .map(event -> {
                    Voyage voyage = new Voyage();
                    // voyage.setEventId(event.getEventId());
                    // voyage.setCode(event.getCode());
                    // voyage.setName(event.getName());
                    // voyage.setBegDate(event.getBegDate());
                    // voyage.setEndDate(event.getEndDate());
                    // voyage.setBegLocation(event.getBegLocation());
                    // voyage.setEndLocation(event.getEndLocation());
                    // voyage.setCruiseCode(event.getCode());
                    // voyage.setCreatedDate(java.time.LocalDateTime.now().toString());
                    // voyage.setComments(event.getComments());
                    return voyage;
                })
                .collect(Collectors.toList());
            voyagePayLoad.setReader(voyages);
            System.out.println("Voyage Reader size " + voyages.size());
            alreadyRead = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return voyagePayLoad;
    }
} 
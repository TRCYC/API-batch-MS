package com.rcyc.batchsystem.reader;

import java.util.List;
import java.util.Map;
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
import com.rcyc.batchsystem.service.AuditService;
import com.rcyc.batchsystem.service.RescoClient;
import java.util.ArrayList;
import java.util.HashMap;

@Component
public class VoyageReader implements ItemReader<DefaultPayLoad<Voyage, Object, Voyage>> {
    private boolean alreadyRead = false;
    @Autowired
    private AuditService auditService;
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
            // auditService.logAudit(jobId)
            ResListDictionary listDictionary = rescoClient.getAllRegions();
            ResListLocation pList = rescoClient.getAllPorts("P");
            ResListLocation oList = rescoClient.getAllPorts("O");
            ResListItinerary evrimaItinerary = rescoClient.getAllItinerariesByFacility(12l);
            ResListItinerary ilmaItinerary = rescoClient.getAllItinerariesByFacility(13l);
            ResListItinerary luminaraItinerary = rescoClient.getAllItinerariesByFacility(93l);
            

            ResListEvent resListEvent = rescoClient.getAllVoyages(1);

            List<EventDetail> eventList = resListEvent.getEventList();
            Map<String,Object> voyageMap = new HashMap<>();
            voyageMap.put("REGIONS", listDictionary);
            voyageMap.put("PORT_P", pList);
            voyageMap.put("PORT_O", oList);
            voyageMap.put("ITINERARY_ILMA", ilmaItinerary);
            voyageMap.put("ITINERARY_LUMINARA", luminaraItinerary);
            voyageMap.put("ITINERARY_EVRIMA", evrimaItinerary);
            voyageMap.put("VOYAGE", resListEvent);
             
            voyagePayLoad.setReader(voyageMap); 
            alreadyRead = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return voyagePayLoad;
    }
} 
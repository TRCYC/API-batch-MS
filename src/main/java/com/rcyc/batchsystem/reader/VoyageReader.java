package com.rcyc.batchsystem.reader;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mysql.cj.log.Log;
import com.rcyc.batchsystem.entity.FeedDateRangeEntity;
import com.rcyc.batchsystem.entity.RegionEntity;
import com.rcyc.batchsystem.model.elastic.Voyage;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.model.resco.EventDetail;
import com.rcyc.batchsystem.model.resco.ResListCategory;
import com.rcyc.batchsystem.model.resco.ResListDictionary;
import com.rcyc.batchsystem.model.resco.ResListEvent;
import com.rcyc.batchsystem.model.resco.ResListItinerary;
import com.rcyc.batchsystem.model.resco.ResListLocation;
import com.rcyc.batchsystem.repository.FeedDateRangeRepository;
import com.rcyc.batchsystem.repository.RegionRepository;
import com.rcyc.batchsystem.service.AuditService;
import com.rcyc.batchsystem.service.RescoClient;
import com.rcyc.batchsystem.service.ScheduledJobService;

import java.util.ArrayList;
import java.util.HashMap;


public class VoyageReader implements ItemReader<DefaultPayLoad<Voyage, Object, Voyage>> { 
    Logger logger = Logger.getLogger(VoyageReader.class.getName());
    private AuditService auditService;
    private RescoClient rescoClient;
    private FeedDateRangeRepository feedDateRangeRepository;
    private RegionRepository regionRepository;
    private ScheduledJobService scheduledJobService;
    private Long jobId;

    public VoyageReader(RescoClient rescoClient, AuditService auditService,
            FeedDateRangeRepository feedDateRangeRepository, RegionRepository regionRepository,ScheduledJobService scheduledJobService, Long jobId) {
        this.rescoClient = rescoClient;
        this.auditService = auditService;
        this.feedDateRangeRepository = feedDateRangeRepository;
        this.regionRepository = regionRepository;
        this.scheduledJobService =scheduledJobService;
        this.jobId = jobId;
    }

    @Override
    public DefaultPayLoad<Voyage, Object, Voyage> read() {
        DefaultPayLoad<Voyage, Object, Voyage> voyagePayLoad = new DefaultPayLoad<>();
        try {
               boolean flag = scheduledJobService.isJobAvailableForExecution(jobId, auditService);
            if (!flag){
                logger.info("Condition failed, so reader return null, trying to exit from job");
                return null;
            }
            List<FeedDateRangeEntity> dateRanges = feedDateRangeRepository.findByType("VOY");
            // auditService.logAudit(jobId)
            List<RegionEntity> regionArrayList = new ArrayList<>();
            Iterable<RegionEntity> regionIteratorList = regionRepository.findAll();
            if (regionIteratorList.iterator().hasNext()) {
                regionIteratorList.forEach(regionArrayList::add);
            }
            ResListDictionary listDictionary = rescoClient.getAllRegions();
            ResListLocation pList = rescoClient.getAllPorts("P");
            ResListLocation oList = rescoClient.getAllPorts("O");
            ResListItinerary evrimaItinerary = rescoClient.getAllItinerariesByFacility(12l);
            ResListItinerary ilmaItinerary = rescoClient.getAllItinerariesByFacility(13l);
            ResListItinerary luminaraItinerary = rescoClient.getAllItinerariesByFacility(93l); 
            String[] currencies = {"USD", "EUR", "GBP", "AUD"};
            Map<String, List<ResListCategory>> allSuites = RescoReaderUtil.fetchCategoriesByCurrency(rescoClient, dateRanges, currencies);
            logger.info("All suites recieved " +String.valueOf(allSuites.size()));

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
            voyageMap.put("REGION_ENTITY", regionArrayList);
            voyageMap.put("SUITES",allSuites);

             
            voyagePayLoad.setReader(voyageMap);  
        } catch (Exception e) {
            e.printStackTrace();
        }
        return voyagePayLoad;
    }
} 
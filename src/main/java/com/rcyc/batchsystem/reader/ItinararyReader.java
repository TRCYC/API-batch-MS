package com.rcyc.batchsystem.reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.rcyc.batchsystem.repository.FeedDateRangeRepository;
import com.rcyc.batchsystem.service.AuditService;
import com.rcyc.batchsystem.service.ElasticService;
import com.rcyc.batchsystem.service.RescoClient;
import com.rcyc.batchsystem.service.ScheduledJobService;

 
public class ItinararyReader implements ItemReader<DefaultPayLoad<Itinerary, Object, Itinerary>> {

    
    private static final Logger logger = LoggerFactory.getLogger(ItinararyReader.class);
    private RescoClient rescoClient;
    private AuditService auditService;
    private ElasticService elasticService;
    private FeedDateRangeRepository feedDateRangeRepository;
    private ScheduledJobService scheduledJobService;
    private boolean alreadyRead = false;
    private Long jobId;

    public ItinararyReader(RescoClient rescoClient, AuditService auditService, ElasticService elasticService,
            FeedDateRangeRepository feedDateRangeRepository,ScheduledJobService scheduledJobService,Long jobId) {
       this.rescoClient = rescoClient;
       this.auditService = auditService;
       this.elasticService = elasticService;
       this.feedDateRangeRepository =feedDateRangeRepository;
       this.scheduledJobService = scheduledJobService;
       this.jobId = jobId;
    }

    @Override
    public DefaultPayLoad<Itinerary, Object, Itinerary> read()
            throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        try {
            boolean flag = scheduledJobService.isJobAvailableForExecution(jobId, auditService);
            if (!flag){
                logger.info("Condition failed, so reader return null, trying to exit from job");
                return null;
            }
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
        
        ResListEvent resListEvent = rescoClient.getAllVoyages(0);

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
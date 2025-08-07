package com.rcyc.batchsystem.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rcyc.batchsystem.model.elastic.Itinerary;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.service.AuditService;
import com.rcyc.batchsystem.service.ElasticService;
import com.rcyc.batchsystem.util.Constants;

public class ItineraryWriter implements ItemWriter<DefaultPayLoad<Itinerary, Object, Itinerary>> {
    
    private ElasticService elasticService;
    private AuditService auditService;

    public ItineraryWriter(AuditService auditService, ElasticService elasticService) {
        this.elasticService = elasticService;
        auditService = auditService;
    }

    @Override
    public void write(List<? extends DefaultPayLoad<Itinerary, Object, Itinerary>> items) throws Exception {
        elasticService.createIndex(Constants.ITINERARY_DEMO_INDEX);
         elasticService.createIndex(Constants.ITINERARY_INDEX);
        elasticService.truncateIndexData(Constants.ITINERARY_DEMO_INDEX);
        for (DefaultPayLoad<Itinerary, Object, Itinerary> payload : items) {
            List<Itinerary> itineraryList = payload.getResponse();
            System.out.println(itineraryList.size());
            if (itineraryList != null && !itineraryList.isEmpty()) {
                elasticService.bulkInsertItineraries(itineraryList, Constants.ITINERARY_DEMO_INDEX);
            }
        }
    }
} 
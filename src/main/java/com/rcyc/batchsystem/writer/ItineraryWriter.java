package com.rcyc.batchsystem.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rcyc.batchsystem.model.elastic.Itinerary;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.service.ElasticService;

@Component
public class ItineraryWriter implements ItemWriter<DefaultPayLoad<Itinerary, Object, Itinerary>> {
    @Autowired
    private ElasticService elasticService;

    @Override
    public void write(List<? extends DefaultPayLoad<Itinerary, Object, Itinerary>> items) throws Exception {
        //elasticService.createTempIndex("itinerary_demo");
        //elasticService.truncateIndexData("itinerary_demo");
        for (DefaultPayLoad<Itinerary, Object, Itinerary> payload : items) {
            List<Itinerary> itineraryList = payload.getResponse();
            System.out.println(itineraryList.size());
            if (itineraryList != null && !itineraryList.isEmpty()) {
                //elasticService.bulkInsertItineraries(itineraryList, "itinerary_demo");
            }
        }
    }
} 
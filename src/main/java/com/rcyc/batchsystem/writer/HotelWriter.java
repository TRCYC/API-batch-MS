package com.rcyc.batchsystem.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rcyc.batchsystem.model.elastic.Hotel;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.service.ElasticService;

import java.util.List;

@Component
public class HotelWriter implements ItemWriter<DefaultPayLoad<Hotel, Object, Hotel>> {
 
    @Autowired
    private ElasticService elasticService;

    @Override
    public void write(List<? extends DefaultPayLoad<Hotel, Object, Hotel>> items) throws Exception {
        System.out.println("Entering Hotel write ");
        elasticService.createIndex("hotel_demo");
        elasticService.truncateIndexData("hotel_demo");
        for (DefaultPayLoad<Hotel, Object, Hotel> payload : items) {
            List<Hotel> hotels = (List<Hotel>) payload.getResponse();
            if (hotels != null) {
                elasticService.bulkInsertHotels(hotels, "hotel_demo");
            }
        }
    }
} 
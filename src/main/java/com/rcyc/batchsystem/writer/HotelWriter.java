package com.rcyc.batchsystem.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rcyc.batchsystem.model.elastic.Hotel;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.service.AuditService;
import com.rcyc.batchsystem.service.ElasticService;
import com.rcyc.batchsystem.util.Constants;

import java.util.List;

public class HotelWriter implements ItemWriter<DefaultPayLoad<Hotel, Object, Hotel>> {
 
    
    private ElasticService elasticService;
    private AuditService auditService;

    public HotelWriter(ElasticService elasticService,AuditService auditService){
        this.auditService = auditService;
        this.elasticService =elasticService;
    }

    @Override
    public void write(List<? extends DefaultPayLoad<Hotel, Object, Hotel>> items) throws Exception {
        System.out.println("Entering Hotel write ");
        elasticService.createIndex(Constants.HOTEL_DEMO_INDEX);
        elasticService.createIndex(Constants.HOTEL_INDEX);
        elasticService.truncateIndexData(Constants.HOTEL_DEMO_INDEX);
        for (DefaultPayLoad<Hotel, Object, Hotel> payload : items) {
            List<Hotel> hotels = (List<Hotel>) payload.getResponse();
            if (hotels != null) {
                elasticService.bulkInsertHotels(hotels, Constants.HOTEL_DEMO_INDEX);
            }
        }
    }
} 
package com.rcyc.batchsystem.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.rcyc.batchsystem.model.elastic.Voyage;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.service.ElasticService;

import java.util.List;

@Component
public class VoyageWriter implements ItemWriter<DefaultPayLoad<Voyage, Object, Voyage>> {
   
    @Autowired
    private ElasticService elasticService;

    @Override
    public void write(List<? extends DefaultPayLoad<Voyage, Object, Voyage>> items) throws Exception {
        elasticService.createTempIndex("voyage_demo");
        elasticService.truncateIndexData("voyage_demo");
        System.out.println("Voyage writer");
        for (DefaultPayLoad<Voyage, Object, Voyage> payload : items) {
            List<Voyage> voyages = (List<Voyage>) payload.getResponse();
            if (voyages != null) {
                System.out.println("From writer >>" + voyages.size());
                try {
                    elasticService.bulkInsertVoyages(voyages, "voyage_demo");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
} 
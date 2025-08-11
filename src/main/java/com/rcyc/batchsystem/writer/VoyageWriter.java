package com.rcyc.batchsystem.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.rcyc.batchsystem.model.elastic.Voyage;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.service.AuditService;
import com.rcyc.batchsystem.service.ElasticService;
import com.rcyc.batchsystem.util.Constants;

import java.util.List;

 
public class VoyageWriter implements ItemWriter<DefaultPayLoad<Voyage, Object, Voyage>> {
   
    private AuditService auditService;
    private ElasticService elasticService;
    private Long jobId;

    public VoyageWriter(ElasticService elasticService, AuditService auditService,Long jobId) {
        this.elasticService = elasticService;
        this.auditService = auditService;
        this.jobId = jobId;
    }

    @Override
    public void write(List<? extends DefaultPayLoad<Voyage, Object, Voyage>> items) throws Exception {
        elasticService.createIndex(Constants.VOYGAE_DEMO_INDEX);
        elasticService.createIndex(Constants.VOYAGE_INDEX);
        elasticService.truncateIndexData(Constants.VOYGAE_DEMO_INDEX);
        System.out.println("Voyage writer");
        for (DefaultPayLoad<Voyage, Object, Voyage> payload : items) {
            List<Voyage> voyages = (List<Voyage>) payload.getResponse();
            if (voyages != null) {
                System.out.println("From writer >>" + voyages.size());
                try {
                    elasticService.bulkInsertVoyages(voyages, Constants.VOYGAE_DEMO_INDEX);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
} 
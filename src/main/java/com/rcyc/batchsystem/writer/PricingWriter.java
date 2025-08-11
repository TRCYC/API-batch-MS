package com.rcyc.batchsystem.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.rcyc.batchsystem.model.elastic.Pricing;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.service.AuditService;
import com.rcyc.batchsystem.service.ElasticService;

import java.util.List;


public class PricingWriter implements ItemWriter<DefaultPayLoad<Pricing, Object, Pricing>> {
   
    
    private ElasticService elasticService;
    private AuditService auditService;
    private Long jobId;

    public PricingWriter(AuditService auditService, ElasticService elasticService, Long jobId) {
        this.elasticService = elasticService;
        this.auditService = auditService;
        this.jobId = jobId;
    }

    @Override
    public void write(List<? extends DefaultPayLoad<Pricing, Object, Pricing>> items) throws Exception {
        elasticService.createIndex("pricing_demo");
          elasticService.truncateIndexData("pricing_demo");
          System.out.println("Pricing writer");
        for (DefaultPayLoad<Pricing, Object, Pricing> payload : items) {
            List<Pricing> pricings = (List<Pricing>) payload.getResponse();
            if (pricings != null) {
                 System.out.println("From writer >>"+pricings.size());
                elasticService.bulkInsertPricing(pricings, "pricing_demo");
            }
        }
    }
} 
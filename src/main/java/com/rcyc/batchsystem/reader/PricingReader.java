package com.rcyc.batchsystem.reader;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rcyc.batchsystem.entity.FeedDateRangeEntity;
import com.rcyc.batchsystem.model.elastic.Pricing;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.model.resco.EventDetail;
import com.rcyc.batchsystem.model.resco.ResListCategory;
import com.rcyc.batchsystem.model.resco.ResListEvent;
import com.rcyc.batchsystem.repository.FeedDateRangeRepository;
import com.rcyc.batchsystem.service.AuditService;
import com.rcyc.batchsystem.service.RescoClient;
import com.rcyc.batchsystem.service.ScheduledJobService;

import java.util.ArrayList;
import com.rcyc.batchsystem.reader.RescoReaderUtil;

 
public class PricingReader implements ItemReader<DefaultPayLoad<Pricing, Object,Pricing>> {

    private Logger logger = Logger.getLogger(PricingReader.class.getName());
    
    private RescoClient rescoClient;
    private AuditService auditService;
    private ScheduledJobService scheduledJobService;
    private Long jobId ;


    @Autowired
    private FeedDateRangeRepository feedDateRangeRepository;

    public PricingReader(RescoClient rescoClient, AuditService auditService, ScheduledJobService scheduledJobService,
            Long jobId) {
         this.rescoClient = rescoClient;
         this.auditService = auditService;
         this.scheduledJobService = scheduledJobService;
         this.jobId = jobId;
    }

    @Override
    public DefaultPayLoad<Pricing, Object, Pricing> read() {
        DefaultPayLoad<Pricing, Object, Pricing> pricingPayLoad = new DefaultPayLoad<>();
        try {
             boolean flag = scheduledJobService.isJobAvailableForExecution(jobId, auditService);
            if (!flag){
                logger.info("Condition failed, so reader return null, trying to exit from job");
                return null;
            }
            List<FeedDateRangeEntity> dateRanges = feedDateRangeRepository.findByType("PRC");
            String[] currencies = {"USD", "EUR", "GBP", "AUD"};
            List<ResListCategory> allCategories = RescoReaderUtil.fetchCategoriesForCurrencies(rescoClient, dateRanges, currencies);

            pricingPayLoad.setReader(allCategories);
            System.out.println("Pricing Reader size "+allCategories.size()); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pricingPayLoad;
    }
} 
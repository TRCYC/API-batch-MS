package com.rcyc.batchsystem.job;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rcyc.batchsystem.model.elastic.Pricing;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.process.PricingProcessor;
import com.rcyc.batchsystem.reader.PricingReader;
import com.rcyc.batchsystem.service.AuditService;
import com.rcyc.batchsystem.service.ElasticService;
import com.rcyc.batchsystem.service.JobStepCallbackListener;
import com.rcyc.batchsystem.service.RescoClient;
import com.rcyc.batchsystem.service.ScheduledJobService;
import com.rcyc.batchsystem.writer.PricingWriter;

@Configuration
public class PricingBatchJob {
    
    @Autowired
    private RescoClient rescoClient;
    @Autowired
    private AuditService auditService;
    @Autowired
    private ElasticService elasticService; 
    @Autowired
    private ScheduledJobService scheduledJobService;
    @Autowired
    private JobStepCallbackListener jobStepCallbackListener;

    @Bean
    public Step pricingStep(StepBuilderFactory stepBuilderFactory,
    ItemReader<DefaultPayLoad<Pricing, Object, Pricing>> pricingStepReader,
    ItemProcessor<DefaultPayLoad<Pricing, Object, Pricing>, DefaultPayLoad<Pricing, Object, Pricing>> pricingStepProcessor,
    ItemWriter<DefaultPayLoad<Pricing, Object, Pricing>> pricingStepWriter) {
        return stepBuilderFactory.get("pricingStep")
                .<DefaultPayLoad<Pricing, Object, Pricing>, DefaultPayLoad<Pricing, Object, Pricing>>chunk(10)
                .reader(pricingStepReader)
                .processor(pricingStepProcessor)
                .writer(pricingStepWriter)
                .listener(jobStepCallbackListener)
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<DefaultPayLoad<Pricing, Object, Pricing>> pricingStepReader(@Value("#{jobParameters['jobId']}") Long jobId) {
        System.out.println("Pricing Reader");
        return new PricingReader(rescoClient,auditService,scheduledJobService,jobId);
    }

    @Bean
    @StepScope
    public ItemProcessor<DefaultPayLoad<Pricing, Object, Pricing>, DefaultPayLoad<Pricing, Object, Pricing>> pricingStepProcessor(@Value("#{jobParameters['jobId']}") Long jobId) {
        System.out.println("Pricing processor");
        PricingProcessor pricingProcessor = new PricingProcessor(auditService,jobId);
        return pricingProcessor.pricingProcessForWrite();
    }

    @Bean
    @StepScope
    public ItemWriter<DefaultPayLoad<Pricing, Object, Pricing>> pricingStepWriter(@Value("#{jobParameters['jobId']}") Long jobId) {
        return new PricingWriter(auditService,elasticService,jobId);
    }
} 
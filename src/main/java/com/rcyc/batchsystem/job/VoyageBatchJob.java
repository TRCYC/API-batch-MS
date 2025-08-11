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

import com.rcyc.batchsystem.model.elastic.Voyage;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.process.VoyageProcessor;
import com.rcyc.batchsystem.reader.VoyageReader;
import com.rcyc.batchsystem.repository.FeedDateRangeRepository;
import com.rcyc.batchsystem.repository.RegionRepository;
import com.rcyc.batchsystem.service.AuditService;
import com.rcyc.batchsystem.service.ElasticService;
import com.rcyc.batchsystem.service.JobStepCallbackListener;
import com.rcyc.batchsystem.service.RescoClient;
import com.rcyc.batchsystem.service.ScheduledJobService;
import com.rcyc.batchsystem.writer.VoyageWriter;

@Configuration
public class VoyageBatchJob {

    @Autowired
    private AuditService auditService;
    @Autowired
    private RescoClient rescoClient;
    @Autowired
    private FeedDateRangeRepository feedDateRangeRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private ScheduledJobService scheduledJobService;
    @Autowired
    private ElasticService elasticService;
    @Autowired
    private JobStepCallbackListener jobStepCallbackListener;

    @Bean
    @StepScope
    public Step voyageStep(StepBuilderFactory stepBuilderFactory,
    ItemReader<DefaultPayLoad<Voyage, Object, Voyage>> voyageStepReader,
    ItemProcessor<DefaultPayLoad<Voyage, Object, Voyage>, DefaultPayLoad<Voyage, Object, Voyage>> voyageStepProcessor,
    ItemWriter<DefaultPayLoad<Voyage, Object, Voyage>> voyageStepWriter) {
        return stepBuilderFactory.get("voyageStep")
                .<DefaultPayLoad<Voyage, Object, Voyage>, DefaultPayLoad<Voyage, Object, Voyage>>chunk(10)
                .reader(voyageStepReader)
                .processor(voyageStepProcessor)
                .writer(voyageStepWriter)
                .listener(jobStepCallbackListener)
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<DefaultPayLoad<Voyage, Object, Voyage>> voyageStepReader(@Value("#{jobParameters['jobId']}") Long jobId) {
        System.out.println("Voyage Reader");
        return new VoyageReader(rescoClient,auditService,feedDateRangeRepository,regionRepository,scheduledJobService,jobId);
    }

    @Bean
    @StepScope
    public ItemProcessor<DefaultPayLoad<Voyage, Object, Voyage>, DefaultPayLoad<Voyage, Object, Voyage>> voyageStepProcessor(@Value("#{jobParameters['jobId']}") Long jobId) {
        System.out.println("Voyage processor");
        VoyageProcessor voyageProcessor =new VoyageProcessor(auditService,jobId);
        return voyageProcessor.voyageProcessForWrite();
    }

    @Bean
    @StepScope
    public ItemWriter<DefaultPayLoad<Voyage, Object, Voyage>> voyageStepWriter(@Value("#{jobParameters['jobId']}") Long jobId) {
        return new VoyageWriter(elasticService,auditService,jobId);
    }
} 
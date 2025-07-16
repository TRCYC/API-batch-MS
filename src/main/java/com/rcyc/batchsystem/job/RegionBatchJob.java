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
import com.rcyc.batchsystem.model.job.RegionPayLoad;
import com.rcyc.batchsystem.process.RegionProcess;
import com.rcyc.batchsystem.reader.RegionApiReader;
import com.rcyc.batchsystem.repository.RegionRepository;
import com.rcyc.batchsystem.service.AuditService;
import com.rcyc.batchsystem.service.ElasticService;
import com.rcyc.batchsystem.service.JobStepCallbackListener; 
import com.rcyc.batchsystem.service.RescoClient;
import com.rcyc.batchsystem.service.ScheduledJobService;
import com.rcyc.batchsystem.writer.RegionWriter;

@Configuration
public class RegionBatchJob {

    // @Autowired
    // private RegionProcess regionProcess;
    // @Autowired
    // private RegionWriter regionWriter;

    @Autowired
    private RescoClient rescoClient;
    @Autowired
    private AuditService auditService;
    @Autowired
    private ElasticService elasticService;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private ScheduledJobService scheduledJobService;
    @Autowired
    private JobStepCallbackListener jobStepCallbackListener;

    // @Bean
    // public Step regionStep(StepBuilderFactory stepBuilderFactory,
    // ItemReader<RegionPayLoad> regionStepReader) {
    // return stepBuilderFactory.get("regionStep")
    // .<RegionPayLoad, RegionPayLoad>chunk(10)
    // .reader(regionStepReader)
    // .processor(regionStepProcessor())
    // .writer(regionStepWriter())
    // .build();
    // }

    @Bean
    @StepScope
    public ItemReader<RegionPayLoad> regionReader(@Value("#{jobParameters['jobId']}") Long jobId) {
        return new RegionApiReader(rescoClient, jobId, auditService, scheduledJobService);
    }

    @Bean
    @StepScope
    public ItemProcessor<RegionPayLoad, RegionPayLoad> regionProcessor(@Value("#{jobParameters['jobId']}") Long jobId) {
        RegionProcess obj = new RegionProcess(jobId, auditService);
        return obj.regionProcessForWrite();
    }

    @Bean
    @StepScope
    public ItemWriter<RegionPayLoad> regionWriter(@Value("#{jobParameters['jobId']}") Long jobId) {
        return new RegionWriter(jobId,elasticService,regionRepository,auditService);
    }


    @Bean
    public Step regionStep(StepBuilderFactory stepBuilderFactory,
            ItemReader<RegionPayLoad> regionReader,
            ItemProcessor<RegionPayLoad, RegionPayLoad> regionProcessor,
            ItemWriter<RegionPayLoad> regionWriter) {
        return stepBuilderFactory.get("regionStep")
                .<RegionPayLoad, RegionPayLoad>chunk(10)
                .reader(regionReader)
                .processor(regionProcessor)
                .writer(regionWriter)
                .listener(jobStepCallbackListener)
                .build();
    }
}

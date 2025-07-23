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

import com.rcyc.batchsystem.model.elastic.ExcursionVoyage;
import com.rcyc.batchsystem.model.elastic.Voyage;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.model.job.RegionPayLoad;
import com.rcyc.batchsystem.process.ExcursionProcess;
import com.rcyc.batchsystem.process.RegionProcess;
import com.rcyc.batchsystem.reader.ExcursionReader;
import com.rcyc.batchsystem.reader.RegionApiReader;
import com.rcyc.batchsystem.repository.RegionRepository;
import com.rcyc.batchsystem.service.AuditService;
import com.rcyc.batchsystem.service.ElasticService;
import com.rcyc.batchsystem.service.JobStepCallbackListener;
import com.rcyc.batchsystem.service.RescoClient;
import com.rcyc.batchsystem.service.ScheduledJobService;
import com.rcyc.batchsystem.writer.ExcursionWriter;
import com.rcyc.batchsystem.writer.RegionWriter;

@Configuration
public class ExcursionBatchJob {

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
     

    @Bean
    @StepScope
    public ItemReader<DefaultPayLoad<ExcursionVoyage, Object, ExcursionVoyage>> excursionVoyageReader(
            @Value("#{jobParameters['jobId']}") Long jobId) {
        return new ExcursionReader(rescoClient, jobId, auditService, scheduledJobService);
    }

    @Bean
    @StepScope
    public ItemProcessor<DefaultPayLoad<ExcursionVoyage, Object, ExcursionVoyage>, DefaultPayLoad<ExcursionVoyage, Object, ExcursionVoyage>> excursionVoyageProcess(
            @Value("#{jobParameters['jobId']}") Long jobId) {
        ExcursionProcess obj = new ExcursionProcess(jobId, auditService);
        return obj.excursionProcessForWrite();
    }

    @Bean
    @StepScope
    public ItemWriter<DefaultPayLoad<ExcursionVoyage, Object, ExcursionVoyage>> excursionVoyageStepWriter(@Value("#{jobParameters['jobId']}") Long jobId) {
        return new ExcursionWriter(jobId,elasticService,regionRepository,auditService);
    }

    @Bean
    public Step excursionVoyageStep(StepBuilderFactory stepBuilderFactory,
            ItemReader<DefaultPayLoad<ExcursionVoyage, Object, ExcursionVoyage>> excursionVoyageReader,
            ItemProcessor<DefaultPayLoad<ExcursionVoyage, Object, ExcursionVoyage>, DefaultPayLoad<ExcursionVoyage, Object, ExcursionVoyage>> excursionVoyageProcess,
            ItemWriter<DefaultPayLoad<ExcursionVoyage, Object, ExcursionVoyage>> excursionVoyageStepWriter) {
        return stepBuilderFactory.get("excursionVoyageStep")
                .<DefaultPayLoad<ExcursionVoyage, Object, ExcursionVoyage>, DefaultPayLoad<ExcursionVoyage, Object, ExcursionVoyage>>chunk(
                        10)
                .reader(excursionVoyageReader)
                .processor(excursionVoyageProcess)
                .writer(excursionVoyageStepWriter)
                .listener(jobStepCallbackListener)
                .build();
    }
}

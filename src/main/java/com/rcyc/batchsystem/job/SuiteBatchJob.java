package com.rcyc.batchsystem.job;

import com.rcyc.batchsystem.model.elastic.Suite;
import com.rcyc.batchsystem.model.elastic.Transfer;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.process.SuiteProcess;
import com.rcyc.batchsystem.reader.SuiteReader;
import com.rcyc.batchsystem.service.*;
import com.rcyc.batchsystem.writer.SuiteWriter;
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

@Configuration
public class SuiteBatchJob {

//    @Autowired
//    private SuiteReader suiteReader;
//    @Autowired
//    private SuiteProcess suiteProcess;
//    @Autowired
//    private SuiteWriter suiteWriter;
    @Autowired
    private RescoClient rescoClient;
    @Autowired
    private ElasticService elasticService;
    @Autowired
    private AuditService auditService;
    @Autowired
    private ScheduledJobService scheduledJobService;
    @Autowired
    private JobStepCallbackListener jobStepCallbackListener;


    @Bean
    public Step suiteStep(StepBuilderFactory stepBuilderFactory,
                          ItemReader<DefaultPayLoad<Suite, Object, Suite>> suiteReader,
                          ItemProcessor<DefaultPayLoad<Suite, Object, Suite>, DefaultPayLoad<Suite, Object, Suite>> suiteProcessor,
                          ItemWriter<DefaultPayLoad<Suite, Object, Suite>> suiteWriter) {
        return stepBuilderFactory.get("suiteStep")
                .<DefaultPayLoad<Suite, Object, Suite>, DefaultPayLoad<Suite, Object, Suite>>chunk(10)
                .reader(suiteReader)
                .processor(suiteProcessor)
                .writer(suiteWriter)
                .listener(jobStepCallbackListener)
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<DefaultPayLoad<Suite, Object, Suite>> suiteReader(@Value("#{jobParameters['jobId']}") Long jobId){
        System.out.println("Suite Reader");
        return new SuiteReader(rescoClient, jobId, auditService, scheduledJobService);
        //return suiteReader;
    }


    @Bean
    @StepScope
    public ItemProcessor<DefaultPayLoad<Suite, Object, Suite>, DefaultPayLoad<Suite, Object, Suite>> suiteProcessor(@Value("#{jobParameters['jobId']}") Long jobId){
        System.out.println("Suite processor");
        SuiteProcess suiteProcess = new SuiteProcess(jobId, auditService);
        return suiteProcess.suiteProcessForWrite();
    }


    @Bean
    @StepScope
    public ItemWriter<DefaultPayLoad<Suite, Object, Suite>> suiteWriter(@Value("#{jobParameters['jobId']}") Long jobId) {
        System.out.println("Suite writer");
        return new SuiteWriter(jobId,elasticService,auditService);
        //return suiteWriter;
    }


}

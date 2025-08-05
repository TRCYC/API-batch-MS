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
import com.rcyc.batchsystem.model.elastic.Port;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.process.PortProcess;
import com.rcyc.batchsystem.process.RegionProcess;
import com.rcyc.batchsystem.reader.PortApiReader;
import com.rcyc.batchsystem.repository.RegionRepository;
import com.rcyc.batchsystem.service.AuditService;
import com.rcyc.batchsystem.service.ElasticService;
import com.rcyc.batchsystem.service.JobStepCallbackListener;
import com.rcyc.batchsystem.service.RescoClient;
import com.rcyc.batchsystem.service.ScheduledJobService;
import com.rcyc.batchsystem.writer.PortWriter;
import com.rcyc.batchsystem.writer.RegionWriter;

@Configuration
public class PortBatchJob {
     
    @Autowired
    private RescoClient rescoClient;
    @Autowired
    private AuditService auditService;
    @Autowired
    private ElasticService elasticService;
    // @Autowired
    // private PortRepository portRepository;
    @Autowired
    private ScheduledJobService scheduledJobService;
    @Autowired
    private JobStepCallbackListener jobStepCallbackListener;


    @Bean    
    @StepScope
    public ItemReader<DefaultPayLoad<Port, Object, Port>> portStepReader(@Value("#{jobParameters['jobId']}") Long jobId) {
        System.out.println("Port Reader");       
        return new PortApiReader(rescoClient,jobId,scheduledJobService,auditService);
    }
     

    @Bean
     @StepScope
    public ItemProcessor<DefaultPayLoad<Port, Object, Port>, DefaultPayLoad<Port, Object, Port>> portStepProcessor(@Value("#{jobParameters['jobId']}") Long jobId) {
        System.out.println("Port processor");
        PortProcess portProcess = new PortProcess(jobId, auditService);
        return portProcess.portProcessForWrite();
    }

    @Bean
     @StepScope
    public ItemWriter<DefaultPayLoad<Port, Object, Port>> portStepWriter(@Value("#{jobParameters['jobId']}") Long jobId) {
        return new PortWriter(jobId,elasticService,auditService);
         
    }

       @Bean
    public Step portStep(StepBuilderFactory stepBuilderFactory,
                ItemReader<DefaultPayLoad<Port, Object, Port>> portStepReader,
                ItemProcessor<DefaultPayLoad<Port, Object, Port>, DefaultPayLoad<Port, Object, Port>> portStepProcessor,
                ItemWriter<DefaultPayLoad<Port, Object, Port>> portStepWriter
    ) {
        return stepBuilderFactory.get("portStep")
                .<DefaultPayLoad<Port, Object, Port>, DefaultPayLoad<Port, Object, Port>>chunk(10)
                .reader(portStepReader)
                .processor(portStepProcessor)
                .writer(portStepWriter)
                .listener(jobStepCallbackListener)
                .build();
    }
} 
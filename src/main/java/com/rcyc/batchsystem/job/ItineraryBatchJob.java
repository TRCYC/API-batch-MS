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
import com.rcyc.batchsystem.model.elastic.Itinerary;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.process.ItineraryProcess;
import com.rcyc.batchsystem.reader.ItinararyReader;
import com.rcyc.batchsystem.repository.FeedDateRangeRepository;
import com.rcyc.batchsystem.service.AuditService;
import com.rcyc.batchsystem.service.ElasticService;
import com.rcyc.batchsystem.service.JobStepCallbackListener;
import com.rcyc.batchsystem.service.RescoClient;
import com.rcyc.batchsystem.service.ScheduledJobService;
import com.rcyc.batchsystem.writer.ItineraryWriter;

@Configuration
public class ItineraryBatchJob {

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
    @Autowired
    private FeedDateRangeRepository feedDateRangeRepository;

 
    @Bean
    @StepScope
    public ItemReader<DefaultPayLoad<Itinerary, Object, Itinerary>> itineraryStepReader(@Value("#{jobParameters['jobId']}") Long jobId) {
        System.out.println("Itinerary Reader");
        return new ItinararyReader(rescoClient,auditService,elasticService,feedDateRangeRepository,scheduledJobService,jobId); 
    }

    @Bean
    @StepScope
    public ItemProcessor<DefaultPayLoad<Itinerary, Object, Itinerary>, DefaultPayLoad<Itinerary, Object, Itinerary>> itineraryStepProcessor(@Value("#{jobParameters['jobId']}") Long jobId) {
        System.out.println("Itinerary processor");
        ItineraryProcess itineraryProcess = new ItineraryProcess(jobId,auditService);
        return itineraryProcess.itineraryProcessForWrite();
    }

    @Bean
    @StepScope
    public ItemWriter<DefaultPayLoad<Itinerary, Object, Itinerary>> itineraryStepWriter() {
        return new ItineraryWriter(auditService,elasticService);
    }

       @Bean
    public Step itineraryStep(StepBuilderFactory stepBuilderFactory,
            ItemReader<DefaultPayLoad<Itinerary, Object, Itinerary>> itineraryStepReader,
            ItemProcessor<DefaultPayLoad<Itinerary, Object, Itinerary>, DefaultPayLoad<Itinerary, Object, Itinerary>> itineraryStepProcessor,
            ItemWriter<DefaultPayLoad<Itinerary, Object, Itinerary>> itineraryStepWriter) {
        return stepBuilderFactory.get("itineraryStep")
                .<DefaultPayLoad<Itinerary, Object, Itinerary>, DefaultPayLoad<Itinerary, Object, Itinerary>>chunk(10)
                .reader(itineraryStepReader)
                .processor(itineraryStepProcessor)
                .writer(itineraryStepWriter)
                .listener(jobStepCallbackListener)
                .build();
    }

} 
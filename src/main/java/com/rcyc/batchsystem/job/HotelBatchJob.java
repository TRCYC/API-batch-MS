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

import com.rcyc.batchsystem.model.elastic.Hotel;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.process.HotelProcessor;
import com.rcyc.batchsystem.reader.HotelReader;
import com.rcyc.batchsystem.service.AuditService;
import com.rcyc.batchsystem.service.ElasticService;
import com.rcyc.batchsystem.service.JobStepCallbackListener;
import com.rcyc.batchsystem.service.RescoClient;
import com.rcyc.batchsystem.service.ScheduledJobService;
import com.rcyc.batchsystem.writer.HotelWriter;

@Configuration
public class HotelBatchJob {

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
    public Step hotelStep(StepBuilderFactory stepBuilderFactory,
            ItemReader<DefaultPayLoad<Hotel, Object, Hotel>> hotelStepReader,
            ItemProcessor<DefaultPayLoad<Hotel, Object, Hotel>, DefaultPayLoad<Hotel, Object, Hotel>> hotelStepProcessor,
            ItemWriter<DefaultPayLoad<Hotel, Object, Hotel>> hotelStepWriter) {
        return stepBuilderFactory.get("hotelStep")
                .<DefaultPayLoad<Hotel, Object, Hotel>, DefaultPayLoad<Hotel, Object, Hotel>>chunk(10)
                .reader(hotelStepReader)
                .processor(hotelStepProcessor)
                .writer(hotelStepWriter)
                .listener(jobStepCallbackListener)
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<DefaultPayLoad<Hotel, Object, Hotel>> hotelStepReader(@Value("#{jobParameters['jobId']}") Long jobId) {
        System.out.println("Hotel Reader");
        return new HotelReader(rescoClient,auditService,scheduledJobService,jobId);
    }

    @Bean
    @StepScope
    public ItemProcessor<DefaultPayLoad<Hotel, Object, Hotel>, DefaultPayLoad<Hotel, Object, Hotel>> hotelStepProcessor(@Value("#{jobParameters['jobId']}") Long jobId) {
        System.out.println("Hotel processor");
        HotelProcessor hotelProcessor = new HotelProcessor(auditService,jobId);
        return hotelProcessor.hotelProcessForWrite();
    }

    @Bean
    @StepScope
    public ItemWriter<DefaultPayLoad<Hotel, Object, Hotel>> hotelStepWriter(@Value("#{jobParameters['jobId']}") Long jobId) {
         System.out.println("Hotel writer");
        return new HotelWriter(elasticService,auditService);
    }
} 
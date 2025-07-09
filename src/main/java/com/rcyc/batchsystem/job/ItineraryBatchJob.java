package com.rcyc.batchsystem.job;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.rcyc.batchsystem.model.elastic.Itinerary;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.process.ItineraryProcess;
import com.rcyc.batchsystem.reader.ItinararyReader;
import com.rcyc.batchsystem.writer.ItineraryWriter;

@Configuration
public class ItineraryBatchJob {

    @Autowired
    private ItinararyReader itinararyReader;
    @Autowired
    private ItineraryProcess itineraryProcess;
    @Autowired
    private ItineraryWriter itineraryWriter;

    @Bean
    public Step itineraryStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("itineraryStep")
                .<DefaultPayLoad<Itinerary, Object, Itinerary>, DefaultPayLoad<Itinerary, Object, Itinerary>>chunk(10)
                .reader(itineraryStepReader(itinararyReader))
                .processor(itineraryStepProcessor())
                .writer(itineraryStepWriter())
                .build();
    }

    @Bean
    public ItemReader<DefaultPayLoad<Itinerary, Object, Itinerary>> itineraryStepReader(ItinararyReader itinararyReader) {
        System.out.println("Itinerary Reader");
        return itinararyReader;
    }

    @Bean
    public ItemProcessor<DefaultPayLoad<Itinerary, Object, Itinerary>, DefaultPayLoad<Itinerary, Object, Itinerary>> itineraryStepProcessor() {
        System.out.println("Itinerary processor");
        return itineraryProcess.itineraryProcessForWrite();
    }

    @Bean
    public ItemWriter<DefaultPayLoad<Itinerary, Object, Itinerary>> itineraryStepWriter() {
        return itineraryWriter;
    }
} 
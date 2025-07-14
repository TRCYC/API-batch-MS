package com.rcyc.batchsystem.job;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rcyc.batchsystem.model.elastic.Voyage;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.process.VoyageProcessor;
import com.rcyc.batchsystem.reader.VoyageReader;
import com.rcyc.batchsystem.writer.VoyageWriter;

@Configuration
public class VoyageBatchJob {

    @Autowired
    private VoyageReader voyageReader;
    @Autowired
    private VoyageProcessor voyageProcessor;
    @Autowired
    private VoyageWriter voyageWriter;

    @Bean
    public Step voyageStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("voyageStep")
                .<DefaultPayLoad<Voyage, Object, Voyage>, DefaultPayLoad<Voyage, Object, Voyage>>chunk(10)
                .reader(voyageStepReader(voyageReader))
                .processor(voyageStepProcessor())
                .writer(voyageStepWriter())
                .build();
    }

    @Bean
    public ItemReader<DefaultPayLoad<Voyage, Object, Voyage>> voyageStepReader(VoyageReader voyageReader) {
        System.out.println("Voyage Reader");
        return voyageReader;
    }

    @Bean
    public ItemProcessor<DefaultPayLoad<Voyage, Object, Voyage>, DefaultPayLoad<Voyage, Object, Voyage>> voyageStepProcessor() {
        System.out.println("Voyage processor");
        return voyageProcessor.voyageProcessForWrite();
    }

    @Bean
    public ItemWriter<DefaultPayLoad<Voyage, Object, Voyage>> voyageStepWriter() {
        return voyageWriter;
    }
} 
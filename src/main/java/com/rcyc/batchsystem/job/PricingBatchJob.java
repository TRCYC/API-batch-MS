package com.rcyc.batchsystem.job;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rcyc.batchsystem.model.elastic.Pricing;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.process.PricingProcessor;
import com.rcyc.batchsystem.reader.PricingReader;
import com.rcyc.batchsystem.writer.PricingWriter;

@Configuration
public class PricingBatchJob {

    @Autowired
    private PricingReader pricingReader;
    @Autowired
    private PricingProcessor pricingProcessor;
    @Autowired
    private PricingWriter pricingWriter;

    @Bean
    public Step pricingStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("pricingStep")
                .<DefaultPayLoad<Pricing, Object, Pricing>, DefaultPayLoad<Pricing, Object, Pricing>>chunk(10)
                .reader(pricingStepReader(pricingReader))
                .processor(pricingStepProcessor())
                .writer(pricingStepWriter())
                .build();
    }

    @Bean
    public ItemReader<DefaultPayLoad<Pricing, Object, Pricing>> pricingStepReader(PricingReader pricingReader) {
        System.out.println("Pricing Reader");
        return pricingReader;
    }

    @Bean
    public ItemProcessor<DefaultPayLoad<Pricing, Object, Pricing>, DefaultPayLoad<Pricing, Object, Pricing>> pricingStepProcessor() {
        System.out.println("Pricing processor");
        return pricingProcessor.pricingProcessForWrite();
    }

    @Bean
    public ItemWriter<DefaultPayLoad<Pricing, Object, Pricing>> pricingStepWriter() {
        return pricingWriter;
    }
} 
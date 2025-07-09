package com.rcyc.batchsystem.job;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rcyc.batchsystem.model.elastic.Hotel;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.process.HotelProcessor;
import com.rcyc.batchsystem.reader.HotelReader;
import com.rcyc.batchsystem.writer.HotelWriter;

@Configuration
public class HotelBatchJob {

    @Autowired
    private HotelReader hotelReader;
    @Autowired
    private HotelProcessor hotelProcessor;
    @Autowired
    private HotelWriter hotelWriter;

    @Bean
    public Step hotelStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("hotelStep")
                .<DefaultPayLoad<Hotel, Object, Hotel>, DefaultPayLoad<Hotel, Object, Hotel>>chunk(10)
                .reader(hotelStepReader(hotelReader))
                .processor(hotelStepProcessor())
                .writer(hotelStepWriter())
                .build();
    }

    @Bean
    public ItemReader<DefaultPayLoad<Hotel, Object, Hotel>> hotelStepReader(HotelReader hotelReader) {
        System.out.println("Hotel Reader");
        return hotelReader;
    }

    @Bean
    public ItemProcessor<DefaultPayLoad<Hotel, Object, Hotel>, DefaultPayLoad<Hotel, Object, Hotel>> hotelStepProcessor() {
        System.out.println("Hotel processor");
        return hotelProcessor.hotelProcessForWrite();
    }

    @Bean
    public ItemWriter<DefaultPayLoad<Hotel, Object, Hotel>> hotelStepWriter() {
        return hotelWriter;
    }
} 
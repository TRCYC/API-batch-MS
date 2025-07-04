package com.rcyc.batchsystem.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchJobConfig {
    
    @Bean
    public Job regionJob(JobBuilderFactory jobBuilderFactory, Step regionStep) {
        return jobBuilderFactory.get("regionJob")
                .start(regionStep)
                .build();
    }

    @Bean
    public Job portJob(JobBuilderFactory jobBuilderFactory, Step portStep) {
        return jobBuilderFactory.get("portJob")
                .start(portStep)
                .build();
    }

    @Bean
    public Job itineraryJob(JobBuilderFactory jobBuilderFactory, Step portStep) {
        return jobBuilderFactory.get("itineraryJob")
                .start(portStep)
                .build();
    }

}

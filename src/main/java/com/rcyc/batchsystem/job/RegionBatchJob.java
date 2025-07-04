package com.rcyc.batchsystem.job;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration; 
import com.rcyc.batchsystem.model.job.RegionPayLoad;
import com.rcyc.batchsystem.process.RegionProcess;
import com.rcyc.batchsystem.reader.RegionApiReader;
import com.rcyc.batchsystem.writer.RegionWriter;

@Configuration
public class RegionBatchJob {

    @Autowired
    private RegionApiReader regionApiReader;
    @Autowired
    private RegionProcess regionProcess;
    @Autowired
    private RegionWriter regionWriter;

    @Bean
    public Step regionStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("regionStep")
                .<RegionPayLoad, RegionPayLoad>chunk(10)
                .reader(regionStepReader(regionApiReader))
                .processor(regionStepProcessor())
                .writer(regionStepWriter())
                .build();
    }

    @Bean
    public ItemReader<RegionPayLoad> regionStepReader(RegionApiReader regionApiReader) {
        System.out.println("Region Reader");
        return regionApiReader;
    }

    @Bean
    public ItemProcessor<RegionPayLoad, RegionPayLoad> regionStepProcessor() {
        System.out.println("Region processore");
        return regionProcess.regionProcessForWrite();
    }

    @Bean
    public ItemWriter<RegionPayLoad> regionStepWriter() {
        return regionWriter;
    }
}

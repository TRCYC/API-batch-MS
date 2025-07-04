package com.rcyc.batchsystem.job;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.rcyc.batchsystem.model.elastic.Port;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.process.PortProcess;
import com.rcyc.batchsystem.reader.PortApiReader;
import com.rcyc.batchsystem.writer.PortWriter;

@Configuration
public class PortBatchJob {

    @Autowired
    private PortApiReader portApiReader;
    @Autowired
    private PortProcess portProcess;
    @Autowired
    private PortWriter portWriter;

    @Bean
    public Step portStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("portStep")
                .<DefaultPayLoad<Port, Object, Port>, DefaultPayLoad<Port, Object, Port>>chunk(10)
                .reader(portStepReader(portApiReader))
                .processor(portStepProcessor())
                .writer(portStepWriter())
                .build();
    }

    @Bean
    public ItemReader<DefaultPayLoad<Port, Object, Port>> portStepReader(PortApiReader portApiReader) {
        System.out.println("Port Reader");
        return portApiReader;
    }

    @Bean
    public ItemProcessor<DefaultPayLoad<Port, Object, Port>, DefaultPayLoad<Port, Object, Port>> portStepProcessor() {
        System.out.println("Port processor");
        return portProcess.portProcessForWrite();
    }

    @Bean
    public ItemWriter<DefaultPayLoad<Port, Object, Port>> portStepWriter() {
        return portWriter;
    }
} 
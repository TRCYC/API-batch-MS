package com.rcyc.batchsystem.job;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rcyc.batchsystem.model.elastic.Transfer;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.process.TransferProcess;
import com.rcyc.batchsystem.reader.TransferReader;
import com.rcyc.batchsystem.writer.TransferWriter;

@Configuration
public class TransferBatchJob {

	@Autowired
	private TransferReader transferReader;
	@Autowired
	private TransferProcess transferProcess;
	@Autowired
	private TransferWriter transferWriter;

	@Bean
	public Step transferStep(StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory.get("transferStep")
				.<DefaultPayLoad<Transfer, Object, Transfer>, DefaultPayLoad<Transfer, Object, Transfer>>chunk(10)
				.reader(transferStepReader(transferReader)).processor(transferStepProcessor())
				.writer(transferStepWriter()).build();
	}

	@Bean
	public ItemReader<DefaultPayLoad<Transfer, Object, Transfer>> transferStepReader(TransferReader transferReader) {
		System.out.println("Transfer Reader");
		return transferReader;
	}

	@Bean
	public ItemProcessor<DefaultPayLoad<Transfer, Object, Transfer>, DefaultPayLoad<Transfer, Object, Transfer>> transferStepProcessor() {
		System.out.println("Transfer processor");
		return transferProcess.transferProcessForWrite();
	}

	@Bean
	public ItemWriter<DefaultPayLoad<Transfer, Object, Transfer>> transferStepWriter() {
		System.out.println("Transfer writer");
		return transferWriter;
	}
}

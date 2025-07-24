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

import com.rcyc.batchsystem.model.elastic.Transfer;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.model.job.RegionPayLoad;
import com.rcyc.batchsystem.process.TransferProcess;
import com.rcyc.batchsystem.reader.RegionApiReader;
import com.rcyc.batchsystem.reader.TransferReader;
import com.rcyc.batchsystem.service.AuditService;
import com.rcyc.batchsystem.service.ElasticService;
import com.rcyc.batchsystem.service.JobStepCallbackListener;
import com.rcyc.batchsystem.service.RescoClient;
import com.rcyc.batchsystem.service.ScheduledJobService;
import com.rcyc.batchsystem.writer.RegionWriter;
import com.rcyc.batchsystem.writer.TransferWriter;

@Configuration
public class TransferBatchJob {

	// @Autowired
	// private TransferReader transferReader;
	// @Autowired
	// private TransferProcess transferProcess;
	// @Autowired
	// private TransferWriter transferWriter;
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
	public Step transferStep(StepBuilderFactory stepBuilderFactory,
			ItemReader<DefaultPayLoad<Transfer, Object, Transfer>> transferReader,
			ItemProcessor<DefaultPayLoad<Transfer, Object, Transfer>, DefaultPayLoad<Transfer, Object, Transfer>> transferProcessor,
			ItemWriter<DefaultPayLoad<Transfer, Object, Transfer>> transferWriter) {
		return stepBuilderFactory.get("transferStep")
				.<DefaultPayLoad<Transfer, Object, Transfer>, DefaultPayLoad<Transfer, Object, Transfer>>chunk(10)
				.reader(transferReader).processor(transferProcessor).writer(transferWriter)
				.listener(jobStepCallbackListener).build();
	}

	@Bean
	@StepScope
	public ItemReader<DefaultPayLoad<Transfer, Object, Transfer>> transferReader(
			@Value("#{jobParameters['jobId']}") Long jobId) {
		System.out.println("Transfer Reader");
		return new TransferReader(rescoClient, jobId, auditService, scheduledJobService);
		// return transferReader;
	}

	@Bean
	@StepScope
	public ItemProcessor<DefaultPayLoad<Transfer, Object, Transfer>, DefaultPayLoad<Transfer, Object, Transfer>> transferProcessor(
			@Value("#{jobParameters['jobId']}") Long jobId) {
		System.out.println("Transfer processor");
		TransferProcess transferProcess = new TransferProcess(jobId, auditService);
		return transferProcess.transferProcessForWrite();
	}

	@Bean
	@StepScope
	public ItemWriter<DefaultPayLoad<Transfer, Object, Transfer>> transferWriter(
			@Value("#{jobParameters['jobId']}") Long jobId) {
		System.out.println("Transfer writer");
		return new TransferWriter(jobId, elasticService, auditService);
		// return transferWriter;
	}
}

package com.rcyc.batchsystem.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rcyc.batchsystem.model.elastic.Transfer;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.repository.RegionRepository;
import com.rcyc.batchsystem.service.AuditService;
import com.rcyc.batchsystem.service.ElasticService;

public class TransferWriter implements ItemWriter<DefaultPayLoad<Transfer, Object, Transfer>> {
	@Autowired
	private ElasticService elasticService;
	private Long jobId;
	private AuditService auditService;

	public TransferWriter(Long jobId, ElasticService elasticService, AuditService auditService) {
		this.jobId = jobId;
		this.elasticService = elasticService;
		this.auditService = auditService;
	}

	@Override
	public void write(List<? extends DefaultPayLoad<Transfer, Object, Transfer>> items) throws Exception {
		System.out.println("Entering Transfer write");
		auditService.logAudit(jobId, "feed_type", "Writing");
		elasticService.createTempIndex("transfer_forkjoin_demo");
		elasticService.truncateIndexData("transfer_forkjoin_demo");
		for (DefaultPayLoad<Transfer, Object, Transfer> payload : items) {
			List<Transfer> transferList = payload.getResponse();
			if (transferList != null && !transferList.isEmpty()) {
				System.out.println("transferList size -->" + transferList.size());
				elasticService.bulkInsertTransfers(transferList, "transfer_forkjoin_demo");
			}
		}
	}
}

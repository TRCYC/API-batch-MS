package com.rcyc.batchsystem.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rcyc.batchsystem.model.elastic.Transfer;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.repository.RegionRepository;
import com.rcyc.batchsystem.service.ElasticService;

@Component
public class TransferWriter implements ItemWriter<DefaultPayLoad<Transfer, Object, Transfer>> {
	@Autowired
	private ElasticService elasticService;

	@Override
	public void write(List<? extends DefaultPayLoad<Transfer, Object, Transfer>> items) throws Exception {
		elasticService.createTempIndex("transfer_demo");
		elasticService.truncateIndexData("transfer_demo");
		for (DefaultPayLoad<Transfer, Object, Transfer> payload : items) {
			List<Transfer> transferList = payload.getResponse();
			System.out.println(transferList.size());
			if (transferList != null && !transferList.isEmpty()) {
				elasticService.bulkInsertTransfers(transferList, "transfer_demo");
			}
		}
	}
}

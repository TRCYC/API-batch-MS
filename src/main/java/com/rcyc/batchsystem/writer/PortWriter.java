package com.rcyc.batchsystem.writer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rcyc.batchsystem.entity.RegionEntity;
import com.rcyc.batchsystem.model.elastic.Region;
import com.rcyc.batchsystem.model.job.RegionPayLoad;
import com.rcyc.batchsystem.repository.RegionRepository;
import com.rcyc.batchsystem.service.AuditService;
import com.rcyc.batchsystem.service.ElasticService;
import com.rcyc.batchsystem.model.elastic.Port;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;


public class PortWriter implements ItemWriter<DefaultPayLoad<Port, Object, Port>> {
    
    private  ElasticService elasticService;
    private  Long jobId;
    private  AuditService auditService;

    public PortWriter(Long jobId, ElasticService elasticService, AuditService auditService) {
        this.jobId = jobId;
        this.elasticService = elasticService;
        this.auditService =auditService;
    }

    @Override
    public void write(List<? extends DefaultPayLoad<Port, Object, Port>> items) throws Exception {
        elasticService.createTempIndex("port_demo");
        elasticService.truncateIndexData("port_demo");
         auditService.logAudit(jobId, "feed_type", "Writing");
        for (DefaultPayLoad<Port, Object, Port> payload : items) {
            List<Port> portList = payload.getResponse();
            System.out.println(portList.size());
            if (portList != null && !portList.isEmpty()) {
                elasticService.bulkInsertPorts(portList, "port_demo");
            }
        }
    }
}

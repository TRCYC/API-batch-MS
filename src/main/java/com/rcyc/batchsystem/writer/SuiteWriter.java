package com.rcyc.batchsystem.writer;

import com.rcyc.batchsystem.model.elastic.Suite;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.service.AuditService;
import com.rcyc.batchsystem.service.ElasticService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


public class SuiteWriter implements ItemWriter<DefaultPayLoad<Suite, Object, Suite>> {
    @Autowired
    private ElasticService elasticService;
    private Long jobId;
    private AuditService auditService;

    public SuiteWriter(Long jobId, ElasticService elasticService, AuditService auditService){
        this.jobId=jobId;
        this.elasticService=elasticService;
        this.auditService=auditService;
    }

    @Override
    public void write(List<? extends DefaultPayLoad<Suite, Object, Suite>> items) throws Exception{
        System.out.println("Entering Suite write");
        auditService.logAudit(jobId, "feed_type", "Writing");
        elasticService.createIndex("suite_demo");
        elasticService.truncateIndexData("suite_demo");

        for(DefaultPayLoad<Suite, Object, Suite> payLoad : items){
            List<Suite> suites=(List<Suite>) payLoad.getResponse();
            if(suites != null){
                System.out.println("From suite >>"+suites.size());
                elasticService.bulkInsertSuite(suites, "suite_demo");
            }
        }
    }
}




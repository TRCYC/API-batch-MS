package com.rcyc.batchsystem.reader;

import com.rcyc.batchsystem.model.elastic.Suite;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.model.resco.EventDetail;
import com.rcyc.batchsystem.model.resco.ResListCategory;
import com.rcyc.batchsystem.model.resco.ResListEvent;
import com.rcyc.batchsystem.service.AuditService;
import com.rcyc.batchsystem.service.RescoClient;
import com.rcyc.batchsystem.service.ScheduledJobService;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class SuiteReader implements ItemReader<DefaultPayLoad<Suite, Object, Suite>> {

    @Autowired
    private RescoClient rescoClient;
    private final Long jobId;
    private boolean alreadyRead = false;
    private AuditService auditService;
    private LocalDateTime today = LocalDateTime.now();
    private ScheduledJobService scheduledJobService;

    public SuiteReader(RescoClient rescoClient, Long jobId, AuditService auditService, ScheduledJobService scheduledJobService){
        this.rescoClient=rescoClient;
        this.jobId=jobId;
        this.auditService =auditService;
        this.scheduledJobService = scheduledJobService;
    }

    @Override
    public DefaultPayLoad<Suite, Object, Suite> read(){
        boolean flag = scheduledJobService.isJobAvailableForExecution(jobId, auditService);
        if (!flag){
            return null;
        }
        DefaultPayLoad<Suite, Object, Suite> suitePayLoad = new DefaultPayLoad<>();
        auditService.logAudit(jobId, "feed_type", today, today, today,"Resco call initiated");
        try {

            ResListEvent resListEvent = rescoClient.getAllVoyages(0);
            List<EventDetail> eventList = resListEvent.getEventList();

            List<ResListCategory> suiteCategories = new ArrayList<>();
            for (EventDetail event : eventList) {
                int voyageId = event.getEventId();
                String voyageCode = event.getCode();
                System.out.println("VoyageCode-" + voyageCode + " ::VoyageId-" + voyageId);
                ResListCategory resListCategory = rescoClient.getSuiteCategory(String.valueOf(event.getEventId()));
                resListCategory.setCruiseCode(event.getCode());
                resListCategory.setCruiseId(String.valueOf(event.getEventId()));
                suiteCategories.add(resListCategory);
            }

            suitePayLoad.setReader(suiteCategories);
            alreadyRead = true;
            System.out.println("SuiteReader total responses: " + suiteCategories.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return suitePayLoad;
    }
}


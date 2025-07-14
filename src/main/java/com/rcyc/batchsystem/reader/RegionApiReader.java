package com.rcyc.batchsystem.reader;

import java.time.LocalDateTime;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.rcyc.batchsystem.model.elastic.Region;
import com.rcyc.batchsystem.model.job.RegionPayLoad;
import com.rcyc.batchsystem.model.resco.ResListDictionary;
import com.rcyc.batchsystem.service.AuditService;
import com.rcyc.batchsystem.service.RescoClient;

 
public class RegionApiReader implements ItemReader<RegionPayLoad> {

    
    private final RescoClient rescoClient;
    private final Long jobId;
    private boolean alreadyRead = false;
    private AuditService auditService;
    private LocalDateTime today = LocalDateTime.now();

    public RegionApiReader(RescoClient rescoClient, Long jobId,AuditService auditService) {
        this.rescoClient = rescoClient;
        this.jobId = jobId;
        this.auditService =auditService;
    }



    @Override
    public RegionPayLoad read()
            throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        try {
            if (alreadyRead)
                return null;
            RegionPayLoad regionPayLoad = new RegionPayLoad();
            regionPayLoad.setRegionReader(getRegionsFromResco());
            alreadyRead = true;
            return regionPayLoad;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new UnsupportedOperationException("Unimplemented method 'read'");
    }

    private ResListDictionary getRegionsFromResco() {
        ResListDictionary listDictionary = new ResListDictionary();
        try {
            auditService.logAudit(jobId, "feed_type", today, today, today,"Resco call initiated");
            listDictionary = rescoClient.getAllRegions();
            if (listDictionary != null && listDictionary.getDictionaryList() != null
                    && listDictionary.getDictionaryList().getDictionary() != null){
                        auditService.logAudit(jobId, "feed_type", today, today, today,"Regions count : "+listDictionary.getDictionaryList().getDictionary().size());
            }else{
                auditService.logAudit(jobId, "feed_type", today, today, today,"Issue while fetching regions : No regions found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDictionary;
    }

}

package com.rcyc.batchsystem.writer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.item.ItemWriter;

import com.rcyc.batchsystem.entity.RegionEntity;
import com.rcyc.batchsystem.model.elastic.ExcursionVoyage;
import com.rcyc.batchsystem.model.elastic.Region;
import com.rcyc.batchsystem.model.elastic.Voyage;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.model.job.RegionPayLoad;
import com.rcyc.batchsystem.repository.RegionRepository;
import com.rcyc.batchsystem.service.AuditService;
import com.rcyc.batchsystem.service.ElasticService;
import com.rcyc.batchsystem.util.Constants;

public class ExcursionWriter implements ItemWriter<DefaultPayLoad<ExcursionVoyage, Object, ExcursionVoyage>>{
private Long jobId;

    private ElasticService elasticService;
    private RegionRepository regionRepository;
    private AuditService auditService;

    


    public ExcursionWriter(Long jobId, ElasticService elasticService, RegionRepository regionRepository,
            AuditService auditService) {
        this.jobId = jobId;
        this.elasticService = elasticService;
        this.regionRepository = regionRepository;
        this.auditService = auditService;
    }

    @Override
    public void write(List<? extends DefaultPayLoad<ExcursionVoyage, Object, ExcursionVoyage>> items) throws Exception {
        elasticService.createIndex(Constants.EXCURSION_DEMO_INDEX);
        elasticService.createIndex(Constants.EXCURSION_INDEX);
        elasticService.truncateIndexData(Constants.EXCURSION_DEMO_INDEX);
        System.out.println("Excursion Voyage writer");
        for (DefaultPayLoad<ExcursionVoyage, Object, ExcursionVoyage> payload : items) {
            List<ExcursionVoyage> excursionVoyages = (List<ExcursionVoyage>) payload.getResponse();
            if (excursionVoyages != null) {
                System.out.println("From Excursion writer >>" + excursionVoyages.size());
                try {
                    elasticService.bulkInsertExcursionVoyages(excursionVoyages, Constants.EXCURSION_DEMO_INDEX);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }



         
    }

     
}

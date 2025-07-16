package com.rcyc.batchsystem.writer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rcyc.batchsystem.entity.RegionEntity;
import com.rcyc.batchsystem.model.elastic.Region;
import com.rcyc.batchsystem.model.job.RegionPayLoad;
import com.rcyc.batchsystem.repository.RegionRepository;
import com.rcyc.batchsystem.service.AuditService;
import com.rcyc.batchsystem.service.ElasticService;

// @Component
// @StepScope
public class RegionWriter implements ItemWriter<RegionPayLoad> {

    private Long jobId;

    private ElasticService elasticService;
    private RegionRepository regionRepository;
    private AuditService auditService;

    


    public RegionWriter(Long jobId, ElasticService elasticService, RegionRepository regionRepository,
            AuditService auditService) {
        this.jobId = jobId;
        this.elasticService = elasticService;
        this.regionRepository = regionRepository;
        this.auditService = auditService;
    }

    @Override
    public void write(List<? extends RegionPayLoad> items) throws Exception {
        Iterable<RegionEntity> regionIteratorList = regionRepository.findAll();
        if (regionIteratorList.iterator().hasNext()) {
            List<RegionEntity> regionArrayList = new ArrayList<>();
            regionIteratorList.forEach(regionArrayList::add);
            auditService.logAudit(jobId, "feed_type", "Writing");
            // elasticService.createTempIndex("region_demo");
            // elasticService.truncateIndexData("region_demo");
            for (RegionPayLoad payload : items) {
                processRegionBeforeInsert(payload.getRegionResponse(), regionArrayList);
                List<Region> regionList = processRegionBeforeInsert(payload.getRegionResponse(), regionArrayList);
                auditService.logAudit(jobId, "feed_type", "ES bulk insertion started");
                // elasticService.bulkInsertRegions(regionList, "region_demo");
                auditService.logAudit(jobId, "feed_type", "ES bulk insertion completed");
            }
            // elasticService.swapIndex
        }
    }

    private List<Region> processRegionBeforeInsert(List<Region> regionResponse,
            List<RegionEntity> regionEntityArrayList) {
        List<Region> processedList = new ArrayList<>();
        if (!regionResponse.isEmpty() && !regionEntityArrayList.isEmpty()) {
            // Build a map for fast lookup
            Map<String, RegionEntity> entityMap = new HashMap<>();
            for (RegionEntity entity : regionEntityArrayList) {
                entityMap.put(entity.getRegionCode().trim().toLowerCase(), entity);
            }

            for (Region regionEach : regionResponse) {
                String code = regionEach.getRegion_code().trim().toLowerCase();
                RegionEntity entity = entityMap.get(code);
                if (entity != null) {
                    Region region = new Region();
                    region.setPortCode(regionEach.getPortCode());
                    region.setCreatedDate(new Date().toString());
                    region.setWeb_region_code(entity.getWebRegionCode());
                    region.setRegion_url(entity.getRegionUrl());
                    region.setRegion_code(regionEach.getRegion_code());
                    region.setRegion_name(entity.getRegionName());
                    processedList.add(region);
                }
            }
        }
        return processedList;
    }

}

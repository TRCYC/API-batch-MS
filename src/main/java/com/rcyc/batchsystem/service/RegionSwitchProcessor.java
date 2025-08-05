package com.rcyc.batchsystem.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rcyc.batchsystem.model.elastic.Region;
import com.rcyc.batchsystem.util.Constants;

@Service("regionSwitch")
public class RegionSwitchProcessor implements JobSwitchService {

    @Autowired
    private ElasticService elasticService;
    @Autowired
    private DataValidationService dataValidationService;

    @Override
    public void doJobSwitch(Long jobId) {
        filterRegionData();
        List<Region> tempRegionList = elasticService.getTempRegionData();
        if (dataValidationService.validateRegionCMS(tempRegionList)) {
            boolean result = dataValidationService.checkRegionDataVariation(tempRegionList);
            System.out.println(result);
            // if(result)
            reindexFromTempToLive();
            // if(result)
            // deleteFromQueue(jobId);
        }

    }

    private void filterRegionData() {
        List<Region> tempRegionList = elasticService.getTempRegionData();
        for (Region tempRegion : tempRegionList) {
            if (tempRegion.getRegion_code() == null || tempRegion.getWeb_region_code() == null
                    || tempRegion.getRegion_code().isEmpty() || tempRegion.getRegion_code().isEmpty()) {
                elasticService.deleteDocument(Constants.REGION_DEMO,tempRegion.getPortCode());
            }
        }
    }

    @Override
    public boolean reindexFromTempToLive() {
        try {

            elasticService.reindexDemoToLive(Constants.REGION_DEMO, Constants.REGION_INDEX);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void deleteFromQueue(Long jobId) {
        dataValidationService.deleteFromQueue(jobId);
    }

    @Override
    public boolean validateCMS() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateRegionCMS'");
    }

    @Override
    public boolean checkDataVariation() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkRegionDataVariation'");
    }

    @Override
    public void filterData() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'filterData'");
    }

}

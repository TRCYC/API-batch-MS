package com.rcyc.batchsystem.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rcyc.batchsystem.model.elastic.Region;
import com.rcyc.batchsystem.util.Constants;

 

@Service
public class JobSwitchProcessor {

    @Autowired
    private ElasticService elasticService;
    @Autowired
    private DataValidationService dataValidationService;
 

    public void doJobSwitch(String type,Long jobId){
        if(type.equalsIgnoreCase(Constants.REGION)){
            filterRegionData();
            List<Region> tempRegionList =  elasticService.getTempRegionData();
            if(dataValidationService.validateRegionCMS(tempRegionList)){
               boolean result = dataValidationService.checkRegionDataVariation(tempRegionList);
               System.out.println(result);
                // if(result)
                    reindexFromTempToLive(Constants.REGION); 
                // if(result)
                    // deleteFromQueue(jobId);   
            }
        }
    }

    private void filterRegionData() {
      List<Region> tempRegionList =  elasticService.getTempRegionData();
        for (Region tempRegion : tempRegionList) {
			if (tempRegion.getRegion_code() == null || tempRegion.getWeb_region_code() == null
					|| tempRegion.getRegion_code().isEmpty() || tempRegion.getRegion_code().isEmpty()) {
				elasticService.deleteRegionDocument(tempRegion.getPortCode());
			}
		}
    }

    private boolean reindexFromTempToLive(String type){
        try{
            if(Objects.equals(type.toLowerCase(), Constants.REGION.toLowerCase()))
            elasticService.reindexDemoToLive(Constants.REGION_DEMO,Constants.REGION_INDEX);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void deleteFromQueue(Long jobId){
        dataValidationService.deleteFromQueue(jobId);
    }
    

}

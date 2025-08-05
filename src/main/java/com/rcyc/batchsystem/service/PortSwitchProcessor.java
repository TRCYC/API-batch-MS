package com.rcyc.batchsystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.rcyc.batchsystem.model.elastic.Port;
import com.rcyc.batchsystem.util.Constants;

@Service("portSwitch")
public class PortSwitchProcessor implements JobSwitchService{

    @Lazy
    @Autowired
    private ElasticService elasticService;
    @Autowired
    private DataValidationService dataValidationService;

    private List<Port> tempFilteredPortList = null;

    @Override
    public boolean checkDataVariation() {
       return dataValidationService.portDataVariation(tempFilteredPortList);
    }

    @Override
    public void deleteFromQueue(Long jobId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void doJobSwitch(Long jobId) {
        filterData();
        validateCMS();
        checkDataVariation();
        reindexFromTempToLive() ;
        deleteFromQueue(jobId);
    }

    @Override
    public void filterData() {

        List<Port> tempPortList = elasticService.getPortData(Constants.PORT_DEMO_INDEX);  
		for (Port tempPort : tempPortList) {
			if (tempPort.getPortCode() == null || !(tempPort.getPortCode().length() == 5)) {
				elasticService.deleteDocument(Constants.PORT_DEMO_INDEX,tempPort.getPortCode());
			}
		}
		tempFilteredPortList = elasticService.getPortData(Constants.PORT_DEMO_INDEX);
        
    }

    @Override
    public boolean reindexFromTempToLive() {
        try{
             elasticService.reindexDemoToLive(Constants.PORT_DEMO_INDEX, Constants.PORT_INDEX);
        }catch(Exception e){
            e.printStackTrace();
        }
       
        return true;
    }

    @Override
    public boolean validateCMS() {
        // dataValidationService.portValidateCMS(tempFilteredPortList);
        return false;
    }

}

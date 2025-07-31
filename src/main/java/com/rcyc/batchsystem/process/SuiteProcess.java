package com.rcyc.batchsystem.process;

import com.rcyc.batchsystem.model.elastic.Suite;
import com.rcyc.batchsystem.model.elastic.VoyageSuite;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.model.resco.Category;
import com.rcyc.batchsystem.model.resco.ResListCategory;
import com.rcyc.batchsystem.model.resco.ResListEvent;
import com.rcyc.batchsystem.service.AuditService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


public class SuiteProcess {

    private Long jobId;
    private AuditService auditService;

    public SuiteProcess(Long jobId,AuditService auditService){
        this.jobId = jobId;
        this.auditService = auditService;
    }

    public ItemProcessor<DefaultPayLoad<Suite, Object, Suite>, DefaultPayLoad<Suite, Object, Suite>> suiteProcessForWrite(){
        return item -> {
            if(item != null && item.getReader() != null){
                auditService.logAudit(jobId, "feed_type", "Processing");
                List<ResListCategory> allCategories = (List<ResListCategory>) item.getReader();
                System.out.println("From Reader ReSize "+allCategories.size());
                List<Suite> allSuites = mapCategoriesToSuite(allCategories);
                item.setResponse(allSuites);
                System.out.println("Suite Size after process--- "+allSuites.size());
                return item;
            }
            return null;
        };
    }

    private List<Suite> mapCategoriesToSuite(List<ResListCategory> categoryResponse){
        List<Suite> suiteList = new ArrayList<>();
        String cruiseCode = "PLACEHOLDER_CRUISE_CODE"; // Replace with actual cruise code if available
        String cruiseId="PLACEHOLDER_CRUISE_CODE";
        for(ResListCategory resListCategory : categoryResponse){
            cruiseCode=resListCategory.getCruiseCode();
            cruiseId= resListCategory.getCruiseId();
            if(resListCategory != null && resListCategory.getCategoryList()!=null){
                Suite suite=new Suite();
                suite.setVoyageId(cruiseId);
                suite.setVoyageCode(cruiseCode);
                suite.setCreatedDate(java.time.LocalDateTime.now().toString());

                List<VoyageSuite> suites=new ArrayList<>();
                for(Category category : resListCategory.getCategoryList()){
                    VoyageSuite voyageSuite=new VoyageSuite();
                    voyageSuite.setSuiteCategoryCode(category.getCode());
                    voyageSuite.setSuiteCategoryType(category.getType());
                    voyageSuite.setSuiteCategoryName(category.getName());
                    voyageSuite.setSuiteId(parseIntSafe(category.getCategoryId()));
                    suites.add(voyageSuite);
                }
                suite.setSuites(suites);
                suiteList.add(suite);
            }
        }
        return suiteList;
    }
    private int parseIntSafe(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }
}

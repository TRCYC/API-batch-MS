package com.rcyc.batchsystem.service;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import com.rcyc.batchsystem.entity.RcycCmsCheck;
import com.rcyc.batchsystem.entity.RcycDataVariationAction;
import com.rcyc.batchsystem.entity.RcycDataVariationConfig;
import com.rcyc.batchsystem.entity.RcycSchedulerHistory;
import com.rcyc.batchsystem.entity.RcycSchedulerQueue;
import com.rcyc.batchsystem.model.elastic.Port;
import com.rcyc.batchsystem.model.elastic.Region;
import com.rcyc.batchsystem.repository.RcycCmsCheckRepository;
import com.rcyc.batchsystem.repository.RcycDataVariationActionRepository;
import com.rcyc.batchsystem.repository.RcycDataVariationConfigRepository;
import com.rcyc.batchsystem.repository.RcycSchedulerHistoryRepository;
import com.rcyc.batchsystem.repository.RcycSchedulerQueueRepository;
import com.rcyc.batchsystem.util.Constants;

@Service
public class DataValidationService {

    Logger log = Logger.getLogger(DataValidationService.class.getName());

    @Autowired
    private RcycCmsCheckRepository rcycCmsCheckRepository;
    @Autowired
    private RcycDataVariationConfigRepository rcycDataVariationConfigRepository;
    @Autowired
    private RcycDataVariationActionRepository rcycDataVariationActionRepository;
    @Lazy
    @Autowired
    private ElasticService elasticService;
    @Autowired
    private RcycSchedulerHistoryRepository rcycSchedulerHistoryRepository;
    @Autowired
    private RcycSchedulerQueueRepository rcycSchedulerQueueRepository;

    String cmsBaseURL = Constants.CMS_BASE_URL;
    HashMap<String, String> regionCodes = new HashMap<>();
    HashMap<String, String> mailContentForvalidation = new HashMap<>();
    HashMap<String, String> portCodes = new HashMap<>();
    // private @Autowired
    // private RestTemplate restTemplate;

    public boolean validateRegionCMS(List<Region> region) {
        try {

            doCriteriaCheck(region, Constants.REGION, 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean doCriteriaCheck(List<Region> tempRegionList, String jobType, int isTaxonomy) {
        boolean result = true;
        String bodyHeader = null;
        mailContentForvalidation.clear();
        List<RcycCmsCheck> criteriaCheck = rcycCmsCheckRepository.findByJobTypeAndDisableAndIsTaxonomy(jobType, 0,
                isTaxonomy);
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new GsonHttpMessageConverter());
        RestTemplate restTemplate = new RestTemplate(messageConverters);
        if (criteriaCheck == null || criteriaCheck.size() == 0) {
            result = false;
        } else {
            for (Region tempRegion : tempRegionList) {
                String url = null;
                RcycCmsCheck taxonomyCriterion = criteriaCheck.get(0);
                if (taxonomyCriterion.getContentUrl() == null || tempRegion.getWeb_region_code() == null) {
                    result = false;
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    url = cmsBaseURL + taxonomyCriterion.getContentUrl().replaceAll(":regioncode",
                            tempRegion.getWeb_region_code());
                    JsonObject[] cmsValidateMapList = restTemplate.getForObject(url, JsonObject[].class);
                    if (cmsValidateMapList == null || cmsValidateMapList.length == 0) {
                        result = false;
                        regionCodes.put(tempRegion.getWeb_region_code(), taxonomyCriterion.getContentName());
                        String values = retrieveValuesFromListMethod(regionCodes, taxonomyCriterion.getContentName());
                        bodyHeader = "<Strong>CMS " + taxonomyCriterion.getContentName()
                                + " Not Found For Region </Strong>";
                        mailContentForvalidation.put(bodyHeader, bodyHeader + " : " + values);
                    } else {

                        if (cmsValidateMapList[0].get("tid") == null
                                || cmsValidateMapList[0].get("tid").toString().isEmpty()) {
                            result = false;

                            regionCodes.put(tempRegion.getWeb_region_code(), taxonomyCriterion.getContentName());// +":"+tempRegion.getWeb_region_code()
                            String values = retrieveValuesFromListMethod(regionCodes,
                                    taxonomyCriterion.getContentName());
                            bodyHeader = "<Strong>CMS " + taxonomyCriterion.getContentName()
                                    + " Not Found For Region </Strong>";
                            mailContentForvalidation.put(bodyHeader, bodyHeader + " : " + values);

                        } else {
                            List<RcycCmsCheck> cmsCriteriaList = rcycCmsCheckRepository
                                    .findByJobTypeAndDisableAndIsTaxonomy(jobType, 0, 0);
                            for (RcycCmsCheck cmsValidationCriteriaDto : cmsCriteriaList) {
                                url = cmsBaseURL + cmsValidationCriteriaDto.getContentUrl()
                                        .replaceAll(":regioncode", tempRegion.getWeb_region_code()).replaceAll(":tid",
                                                cmsValidateMapList[0].getAsJsonObject().get("tid").getAsInt() + "");

                                JsonObject[] responseCMS = restTemplate.getForObject(url, JsonObject[].class);

                                if (responseCMS == null || responseCMS.length == 0) {
                                    result = false;

                                    regionCodes.put(tempRegion.getWeb_region_code(),
                                            cmsValidationCriteriaDto.getContentName());// +":"+tempRegion.getWeb_region_code()
                                    String values = retrieveValuesFromListMethod(regionCodes,
                                            cmsValidationCriteriaDto.getContentName());
                                    bodyHeader = "<Strong>CMS " + cmsValidationCriteriaDto.getContentName()
                                            + " Not Found For Region </Strong>";
                                    mailContentForvalidation.put(bodyHeader, bodyHeader + " : " + values);

                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }


//     public void portValidateCMS(List<Port> tempFilteredPortList) {
//         try {
//             boolean result = true;
//             String bodyHeader = null;
//             mailContentForvalidation.clear();
//             List<RcycCmsCheck> criteriaCheck = rcycCmsCheckRepository.findByJobTypeAndDisableAndIsTaxonomy(
//                     Constants.PORT, 0,
//                     1);
//             List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//             messageConverters.add(new GsonHttpMessageConverter());
//             RestTemplate restTemplate = new RestTemplate(messageConverters);
//              if (criteriaCheck == null || criteriaCheck.size() == 0) {
//             result = false;
//         } else {
//             for (Port tempPort : tempFilteredPortList) {
//                 String url = null;

//                 if (tempPort.getPortCode() == null || tempPort.getPortCode().length() <= 3) {
// 					elasticService.deleteDocument(Constants.PORT_DEMO_INDEX,tempPort.getPortCode());
// 				}
//                 RcycCmsCheck taxonomyCriterion = criteriaCheck.get(0);
//                 url = cmsBaseURL
// 						+ taxonomyCriterion.getContentUrl().replaceAll(":portcode", tempPort.getPortCode());
//                 JsonObject[] cmsValidateMapList = restTemplate.getForObject(url, JsonObject[].class);
//                 if (cmsValidateMapList == null || cmsValidateMapList.length == 0) {
// 					log.info("CMS Taxonomy Content Not Found For Port Code:" + tempPort.getPortCode());
// 					result = false;
// //					mailSmtp.SendMailSmtp(
// //							"CMS Taxonomy Content Not Found For Port Code:" + tempPort.getPortCode()
// //							+ "\n request url:" + url,
// //							"CMS Taxonomy Content Not Found For Port Code:" + tempPort.getPortCode(),true);
// 					portCodes.put(tempPort.getPortCode(), taxonomyCriterion.getContentName());
// 					String values = retrieveValuesFromListMethod(portCodes, taxonomyCriterion.getContentName());
// 					bodyHeader = "<Strong>CMS " + taxonomyCriterion.getContentName()
// 							+ " Not Found For Port Code</Strong>";
// 					mailContentForvalidation.put(bodyHeader, bodyHeader + " : " + values + "<br>");
// 				}else{
                
//                 }

//             }
//         }

//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }


    private String retrieveValuesFromListMethod(HashMap map, String content) {
        Set keys = map.keySet();
        Iterator itr = keys.iterator();
        String key;
        String value;
        String response = "";
        while (itr.hasNext()) {
            key = (String) itr.next();
            value = (String) map.get(key);
            if (value.equalsIgnoreCase(content)) {
                response = response + "<br> " + key;
            }
        }

        return response;
    }

    public boolean checkRegionDataVariation(List<Region> tempRegionList) {
        if (!tempRegionList.isEmpty()) {
            List<Region> regions = elasticService.getRegionData();
            System.out.println("Temp" + tempRegionList.size() + ", Live" + regions.size());
            List<RcycDataVariationConfig> rcycVariationCriterias = rcycDataVariationConfigRepository
                    .findByJobTypeAndDisableFalse(Constants.REGION);
            List<RcycDataVariationAction> rcycDataVariationActionList = rcycDataVariationActionRepository
                    .findByIdVariation(rcycVariationCriterias.get(0).getIdVariation());
            if (!rcycDataVariationActionList.isEmpty()) {
                String expression = rcycDataVariationActionList.get(0).getActionCondition();
                expression = expression.replace("${result}",
                        String.valueOf(Math.abs(tempRegionList.size() - regions.size())));
                return Constants.evaluateExpression(expression);
            }
        }

        return false;
    }

    public boolean portDataVariation(List<Port> tempPortList) {
        if (!tempPortList.isEmpty()) {
            List<Port> realData = elasticService.getPortData(Constants.PORT_INDEX);
            System.out.println("Temp" + tempPortList.size() + ", Live" + realData.size());
            List<RcycDataVariationConfig> rcycVariationCriterias = rcycDataVariationConfigRepository
                    .findByJobTypeAndDisableFalse(Constants.PORT);
            List<RcycDataVariationAction> rcycDataVariationActionList = rcycDataVariationActionRepository
                    .findByIdVariation(rcycVariationCriterias.get(0).getIdVariation());
            if (!rcycDataVariationActionList.isEmpty()) {
                String expression = rcycDataVariationActionList.get(0).getActionCondition();
                expression = expression.replace("${result}",
                        String.valueOf(Math.abs(tempPortList.size() - realData.size())));
                return Constants.evaluateExpression(expression);
            }
        }

        return false;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteFromQueue(Long jobId) {
        RcycSchedulerHistory history = rcycSchedulerHistoryRepository.findByJobId(jobId.intValue());
        history.setStatus(Constants.SUCCESS);
        RcycSchedulerQueue schedulerQueue = rcycSchedulerQueueRepository
                .findOldestBySchedulerId(history.getSchedulerId());
        rcycSchedulerQueueRepository.delete(schedulerQueue);

    }

    

}

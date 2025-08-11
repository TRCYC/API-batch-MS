package com.rcyc.batchsystem.process;

import org.springframework.stereotype.Component;
import org.springframework.batch.item.ItemProcessor;

import com.rcyc.batchsystem.model.resco.Category;
import com.rcyc.batchsystem.model.resco.ResListCategory;
import com.rcyc.batchsystem.model.elastic.Pricing;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import java.util.ArrayList;
import java.util.List;
import com.rcyc.batchsystem.model.resco.Rate;
import com.rcyc.batchsystem.model.resco.TravelerFee;
import com.rcyc.batchsystem.service.AuditService;


public class PricingProcessor {

    private AuditService auditService;
    private Long jobId;

    public PricingProcessor(AuditService auditService,Long jobId){
        this.auditService = auditService;
        this.jobId = jobId;
    }

    public ItemProcessor<DefaultPayLoad<Pricing, Object, Pricing>, DefaultPayLoad<Pricing, Object, Pricing>> pricingProcessForWrite() {
        return item -> {
            System.out.println(item);
            if (item != null && item.getReader() != null) {
                List<ResListCategory> allCategories = (List<ResListCategory>) item.getReader();
                System.out.println("From Reader ResSize "+allCategories.size());
                List<Pricing> allPricings = mapCategoriesToPricing(allCategories);
                DefaultPayLoad<Pricing, Object, Pricing> pricingPayload = new DefaultPayLoad<>();
                pricingPayload.setResponse(allPricings);
                System.out.println("Pricing Size "+allPricings.size()+"");
                return pricingPayload;
            }
            return null;
        };
    }

    private List<Pricing> mapCategoriesToPricing(List<ResListCategory> categoryResponses) {
        List<Pricing> pricingList = new ArrayList<>();
        String cruiseCode = "PLACEHOLDER_CRUISE_CODE"; // Replace with actual cruise code if available
        for (ResListCategory resListCategory : categoryResponses) {
            cruiseCode = resListCategory.getCruiseCode();
            if (resListCategory != null && resListCategory.getCategoryList() != null) {
                for (Category category : resListCategory.getCategoryList()) {
                    Integer totalAvailUnits = null;
                    if (category.getAvailUnits() != 0) {
                        totalAvailUnits = category.getAvailUnits();
                    }
                    if (category.getRateList() != null) {
                        for (Rate rate : category.getRateList()) {
                            if (rate.getBaseAmount() != null) {
                                Pricing pricing = new Pricing();
                                // pricePerson1 = baseAmount / 2
                                Long pricePerson1 = 0l;
                                try {
                                    pricePerson1 = Long.parseLong(rate.getBaseAmount()) / 2;
                                    pricing.setPricePerson1(pricePerson1);
                                } catch (NumberFormatException e) {
                                    pricing.setPricePerson1(Long.valueOf(rate.getBaseAmount()));
                                }
                                pricing.setPricingId(category.getCategoryId());
                                pricing.setDescription(category.getName());
                                pricing.setCurrency(rate.getPriceCurrency() != null ? rate.getPriceCurrency() : "USD");
                                pricing.setSuiteAvailability(totalAvailUnits != null ? String.valueOf(totalAvailUnits) : "0");
                                pricing.setRateCode(rate.getCode());
                                pricing.setRateName(rate.getName());
                                pricing.setCreatedDate(java.time.LocalDateTime.now().toString());
                                pricing.setCategoryCode(category.getCode());
                                pricing.setCruiseCode(cruiseCode);
                                pricing.setCategoryName(category.getName());
                                if (rate.getTravelerFeeList() != null && !rate.getTravelerFeeList().isEmpty()) {
                                    TravelerFee travelerFee = rate.getTravelerFeeList().get(0);
                                    if (travelerFee.getAgeGroup() != null) {
                                        pricing.setAgeCategory(travelerFee.getAgeGroup());
                                    }
                                }
                                pricingList.add(pricing);
                            }
                        }
                    }
                }
            }
        }
        return pricingList;
    }
} 
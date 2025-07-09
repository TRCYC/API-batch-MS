package com.rcyc.batchsystem.process;

import org.springframework.stereotype.Component;
import org.springframework.batch.item.ItemProcessor;
import com.rcyc.batchsystem.model.resco.ResListCategory;
import com.rcyc.batchsystem.model.elastic.Pricing;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import java.util.ArrayList;
import java.util.List;

@Component
public class PricingProcessor {
    public ItemProcessor<DefaultPayLoad<List<ResListCategory>, Object, List<ResListCategory>>, DefaultPayLoad<Pricing, Object, Pricing>> pricingProcessForWrite() {
        return item -> {
            if (item != null && item.getResponse() != null) {
                List<ResListCategory> allCategories = item.getResponse();
                List<Pricing> allPricings = mapCategoriesToPricing(allCategories);
                DefaultPayLoad<Pricing, Object, Pricing> newPayload = new DefaultPayLoad<>();
                newPayload.setResponse(allPricings);
                return newPayload;
            }
            return null;
        };
    }

    private List<Pricing> mapCategoriesToPricing(List<ResListCategory> categoryResponses) {
        List<Pricing> pricingList = new ArrayList<>();
        for (ResListCategory resListCategory : categoryResponses) {
            if (resListCategory != null && resListCategory.getCategoryList() != null) {
                for (ResListCategory.Category category : resListCategory.getCategoryList()) {
                    Pricing pricing = new Pricing();
                    pricing.setPricingId(category.getCategoryId());
                    pricing.setDescription(category.getName());
                    // Optionally set currency if you can track it here
                    pricingList.add(pricing);
                }
            }
        }
        return pricingList;
    }
} 
package com.rcyc.batchsystem.reader;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rcyc.batchsystem.entity.FeedDateRangeEntity;
import com.rcyc.batchsystem.model.elastic.Pricing;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.model.resco.EventDetail;
import com.rcyc.batchsystem.model.resco.ResListCategory;
import com.rcyc.batchsystem.model.resco.ResListEvent;
import com.rcyc.batchsystem.repository.FeedDateRangeRepository;
import com.rcyc.batchsystem.service.RescoClient;
import java.util.ArrayList;

@Component
public class PricingReader implements ItemReader<DefaultPayLoad<Pricing, Object,Pricing>> {
    private boolean alreadyRead = false;

    @Autowired
    private RescoClient rescoClient;
    @Autowired
    private FeedDateRangeRepository feedDateRangeRepository;

    @Override
    public DefaultPayLoad<Pricing, Object, Pricing> read() {
        DefaultPayLoad<Pricing, Object, Pricing> pricingPayLoad = new DefaultPayLoad<>();
        try {
            if (alreadyRead)
                return null;
            List<FeedDateRangeEntity> dateRanges = feedDateRangeRepository.findByType("PRC")   ;  
            ResListEvent resListEvent = rescoClient.getAllVoyages(1);
            List<EventDetail> eventList = resListEvent.getEventList();
            String[] currencies = {"USD", "EUR", "GBP", "AUD"};
            List<ResListCategory> allCategories = new ArrayList<>();
            FeedDateRangeEntity dateRangeEntity = dateRanges.get(0);
            for (String currency : currencies) {
                List<ResListCategory> categoryResponses = eventList.parallelStream()
                    .filter(event->dateRangeEntity.isBegDateOnOrAfterStartAt(event.getBegDate()))
                    .map(event -> {
                        ResListCategory resListCategory = rescoClient.getSuiteByCurrency(currency, String.valueOf(event.getEventId()), 1);
                        resListCategory.setCruiseCode(event.getCode());
                        return resListCategory;
                    })
                    .collect(Collectors.toList());
                allCategories.addAll(categoryResponses);
            }

            pricingPayLoad.setReader(allCategories);
            System.out.println("Pricing Reader size "+allCategories.size());
            alreadyRead = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pricingPayLoad;
    }
} 
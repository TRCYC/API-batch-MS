package com.rcyc.batchsystem.reader;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rcyc.batchsystem.model.elastic.Pricing;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.model.resco.EventDetail;
import com.rcyc.batchsystem.model.resco.ResListCategory;
import com.rcyc.batchsystem.model.resco.ResListEvent;
import com.rcyc.batchsystem.service.RescoClient;
import java.util.ArrayList;

@Component
public class PricingReader implements ItemReader<DefaultPayLoad<List<ResListCategory>, Object, List<ResListCategory>>> {
    private boolean alreadyRead = false;

    @Autowired
    private RescoClient rescoClient;

    @Override
    public DefaultPayLoad<List<ResListCategory>, Object, List<ResListCategory>> read() {
        DefaultPayLoad<List<ResListCategory>, Object, List<ResListCategory>> pricingPayLoad = new DefaultPayLoad<>();
        try {
            if (alreadyRead)
                return null;
            ResListEvent resListEvent = rescoClient.getAllVoyages(1);
            List<EventDetail> eventList = resListEvent.getEventList();
            String[] currencies = {"USD", "EUR", "GBP", "AUD"};
            List<ResListCategory> allCategories = new ArrayList<>();

            for (String currency : currencies) {
                List<ResListCategory> categoryResponses = eventList.parallelStream()
                    .map(event -> rescoClient.getSuiteByCurrency(currency, String.valueOf(event.getEventId()), 1))
                    .collect(Collectors.toList());
                allCategories.addAll(categoryResponses);
            }

            pricingPayLoad.setReader(allCategories);
            alreadyRead = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pricingPayLoad;
    }
} 
package com.rcyc.batchsystem.reader;

import com.rcyc.batchsystem.entity.FeedDateRangeEntity;
import com.rcyc.batchsystem.model.resco.EventDetail;
import com.rcyc.batchsystem.model.resco.ResListCategory;
import com.rcyc.batchsystem.model.resco.ResListEvent;
import com.rcyc.batchsystem.service.RescoClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RescoReaderUtil {
    public static List<ResListCategory> fetchCategoriesForCurrencies(
            RescoClient rescoClient,
            List<FeedDateRangeEntity> dateRanges,
            String[] currencies) {
        ResListEvent resListEvent = rescoClient.getAllVoyages(1);
        List<EventDetail> eventList = resListEvent.getEventList();
        List<ResListCategory> allCategories = new ArrayList<>();
        FeedDateRangeEntity dateRangeEntity = dateRanges.get(0);

        for (String currency : currencies) {
            List<ResListCategory> categoryResponses = eventList.parallelStream()
                    .filter(event -> dateRangeEntity.isBegDateOnOrAfterStartAt(event.getBegDate()))
                    .map(event -> {
                        ResListCategory resListCategory = rescoClient.getSuiteByCurrency(currency,
                                String.valueOf(event.getEventId()), 1);
                        resListCategory.setCruiseCode(event.getCode());
                        return resListCategory;
                    })
                    .collect(Collectors.toList());
            allCategories.addAll(categoryResponses);
        }
        return allCategories;
    }

    public static Map<String, List<ResListCategory>> fetchCategoriesByCurrency(
            RescoClient rescoClient,
            List<FeedDateRangeEntity> dateRanges,
            String[] currencies) {
        ResListEvent resListEvent = rescoClient.getAllVoyages(1);
        List<EventDetail> eventList = resListEvent.getEventList();
        FeedDateRangeEntity dateRangeEntity = dateRanges.get(0);

        Map<String, List<ResListCategory>> currencyCategoryMap = new HashMap<>();

        for (String currency : currencies) {
            List<ResListCategory> categoryResponses = eventList.parallelStream()
                    .filter(event -> dateRangeEntity.isBegDateOnOrAfterStartAt(event.getBegDate()))
                    .map(event -> {
                        ResListCategory resListCategory = rescoClient.getSuiteByCurrency(currency,
                                String.valueOf(event.getEventId()), 1);
                        resListCategory.setCruiseCode(event.getCode());
                        return resListCategory;
                    })
                    .collect(Collectors.toList());
            currencyCategoryMap.put(currency, categoryResponses);
        }
        return currencyCategoryMap;
    }
}
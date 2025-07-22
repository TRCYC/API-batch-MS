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
import com.rcyc.batchsystem.reader.RescoReaderUtil;

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
            List<FeedDateRangeEntity> dateRanges = feedDateRangeRepository.findByType("PRC");
            String[] currencies = {"USD", "EUR", "GBP", "AUD"};
            List<ResListCategory> allCategories = RescoReaderUtil.fetchCategoriesForCurrencies(rescoClient, dateRanges, currencies);

            pricingPayLoad.setReader(allCategories);
            System.out.println("Pricing Reader size "+allCategories.size());
            alreadyRead = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pricingPayLoad;
    }
} 
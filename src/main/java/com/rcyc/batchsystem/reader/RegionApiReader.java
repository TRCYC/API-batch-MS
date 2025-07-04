package com.rcyc.batchsystem.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.rcyc.batchsystem.model.elastic.Region;
import com.rcyc.batchsystem.model.job.RegionPayLoad;
import com.rcyc.batchsystem.model.resco.ResListDictionary;
import com.rcyc.batchsystem.service.RescoClient;

@Component
public class RegionApiReader implements ItemReader<RegionPayLoad> {

    @Autowired
    private RescoClient rescoClient;
    private boolean alreadyRead = false;

    @Override
    public RegionPayLoad read()
            throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        try {
            if (alreadyRead)
                return null;
            RegionPayLoad regionPayLoad = new RegionPayLoad();
            regionPayLoad.setRegionReader(getRegionsFromResco());
            alreadyRead = true;
            return regionPayLoad;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new UnsupportedOperationException("Unimplemented method 'read'");
    }

    private ResListDictionary getRegionsFromResco() {
        ResListDictionary listDictionary = new ResListDictionary();
        try {
            listDictionary = rescoClient.getAllRegions();
            if (listDictionary != null && listDictionary.getDictionaryList() != null
                    && listDictionary.getDictionaryList().getDictionary() != null)
                listDictionary.getDictionaryList().getDictionary().stream().forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDictionary;
    }

}

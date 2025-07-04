package com.rcyc.batchsystem.process;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.rcyc.batchsystem.model.elastic.Region;
import com.rcyc.batchsystem.model.job.RegionPayLoad;
import com.rcyc.batchsystem.model.job.RegionResponse;
import com.rcyc.batchsystem.model.resco.Dictionary;
import com.rcyc.batchsystem.model.resco.ResListDictionary;

@Component
public class RegionProcess {

    public ItemProcessor<RegionPayLoad, RegionPayLoad> regionProcessForWrite() {
        return item -> {
            if (item != null && item.getRegionReader() != null) {
                ResListDictionary  listCategory = (ResListDictionary) item.getRegionReader();
                List<Region> processedList = listCategory.getDictionaryList().getDictionary()
                .stream()
                .map(dictionary->getAsRegion(dictionary))
                .collect(Collectors.toList());
                System.out.println(processedList.size());

                item.setRegionResponse(processedList); // bind back
            }
            return item;
        };
    }

    private Region getAsRegion(Dictionary dictionary){
        Region region =new Region();
        region.setCreatedDate(new Date().toString());
        region.setPortCode(dictionary.getCode());
        region.setRegion_code(dictionary.getCode());
        region.setWeb_region_code(dictionary.getCode());
        region.setRegion_name(dictionary.getName());
        return region;
    }

}

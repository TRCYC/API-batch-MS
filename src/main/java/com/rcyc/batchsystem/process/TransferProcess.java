package com.rcyc.batchsystem.process;

import java.util.List;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.rcyc.batchsystem.model.elastic.Transfer;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.model.resco.LocationList;
import com.rcyc.batchsystem.model.resco.ResListLocation;

@Component
public class TransferProcess {

	public ItemProcessor<DefaultPayLoad<Transfer, Object, Transfer>, DefaultPayLoad<Transfer, Object, Transfer>> transferProcessForWrite() {
        return item -> {
            if (item != null && item.getReader() != null) {
                ResListLocation listCategory = (ResListLocation) item.getReader();
                LocationList locationList = listCategory.getLocationList();
                List<Transfer> processedList = null;
                /*if (locationList != null && locationList.getLocations() != null) {
                    processedList = locationList.getLocations().stream()
                        .map(this::getAsPort)
                        .collect(Collectors.toList());
                } else {
                    processedList = java.util.Collections.emptyList();
                }*/
                System.out.println(processedList.size());
                //item.setResponse(processedList); // bind back
            }
            return item;
        };
    }
}

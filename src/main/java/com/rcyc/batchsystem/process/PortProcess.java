package com.rcyc.batchsystem.process;

import org.springframework.stereotype.Component;
import com.rcyc.batchsystem.model.elastic.Region;
import com.rcyc.batchsystem.model.job.RegionPayLoad;
import com.rcyc.batchsystem.model.job.RegionResponse;
import com.rcyc.batchsystem.model.resco.Dictionary;
import com.rcyc.batchsystem.model.resco.ResListDictionary;
import org.springframework.batch.item.ItemProcessor;
import java.util.List;
import java.util.stream.Collectors;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.model.elastic.Port;
import com.rcyc.batchsystem.model.resco.Location;
import com.rcyc.batchsystem.model.resco.LocationList;
import com.rcyc.batchsystem.model.resco.ResListLocation;

@Component
public class PortProcess {
    public ItemProcessor<DefaultPayLoad<Port, Object, Port>, DefaultPayLoad<Port, Object, Port>> portProcessForWrite() {
        return item -> {
            if (item != null && item.getReader() != null) {
                ResListLocation listCategory = (ResListLocation) item.getReader();
                LocationList locationList = listCategory.getLocationList();
                List<Port> processedList = null;
                if (locationList != null && locationList.getLocations() != null) {
                    processedList = locationList.getLocations().stream()
                        .map(this::getAsPort)
                        .collect(Collectors.toList());
                } else {
                    processedList = java.util.Collections.emptyList();
                }
                System.out.println(processedList.size());
                item.setResponse(processedList); // bind back
            }
            return item;
        };
    }

    private Port getAsPort(Location location) {
        Port port = new Port();
        port.setPortCode(location.getCode());
        port.setPortName(location.getName());
        port.setPortLatitude(location.getLatitude());
        port.setPortLongitude(location.getLongitude());
        port.setCountryCode(location.getCountry());
        port.setTimeZone(location.getTimeZone());
        port.setEnabled(String.valueOf(location.getEnabled()));
        port.setCreatedDate(new java.util.Date().toString());
        port.setPortRegion(location.getRegion());
        port.setPortId(location.getLocationId());
        return port;
    }
}

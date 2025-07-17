package com.rcyc.batchsystem.process;

import org.springframework.stereotype.Component;
import org.springframework.batch.item.ItemProcessor;

import com.rcyc.batchsystem.model.elastic.Voyage;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.model.resco.ResListDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class VoyageProcessor {
    public ItemProcessor<DefaultPayLoad<Voyage, Object, Voyage>, DefaultPayLoad<Voyage, Object, Voyage>> voyageProcessForWrite() {
        return item -> {
            System.out.println(item);
            if (item != null && item.getReader() != null) {
                List<Voyage> allVoyages = (List<Voyage>) item.getReader();

                Map<String,Object> mapObject = (Map<String, Object>) item.getReader();
                ResListDictionary listDictionary = (ResListDictionary) mapObject.get("REGIONS");
                

                System.out.println("From Reader VoyageSize " + allVoyages.size());
                DefaultPayLoad<Voyage, Object, Voyage> voyagePayload = new DefaultPayLoad<>();
                voyagePayload.setResponse(allVoyages);
                System.out.println("Voyage Size " + allVoyages.size());
                return voyagePayload;
            }
            return null;
        };
    }
} 
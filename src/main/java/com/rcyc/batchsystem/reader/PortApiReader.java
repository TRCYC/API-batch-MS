package com.rcyc.batchsystem.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rcyc.batchsystem.model.elastic.Port;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.model.resco.ResListLocation;
import com.rcyc.batchsystem.service.RescoClient;

@Component
public class PortApiReader implements ItemReader<DefaultPayLoad<Port, Object, Port>> {

    @Autowired
    private RescoClient rescoClient;
    private boolean alreadyRead = false;

    @Override
    public DefaultPayLoad<Port, Object, Port> read() {
        DefaultPayLoad<Port, Object, Port> portPayLoad = new DefaultPayLoad<>();
        try {
            if (alreadyRead)
                return null;
            portPayLoad.setReader(getPortFromResco());
            alreadyRead = true;
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return portPayLoad;
    }

    private ResListLocation getPortFromResco() {
        ResListLocation listLocation = new ResListLocation();
        try {
            ResListLocation pList = rescoClient.getAllPorts("P");
            ResListLocation oList = rescoClient.getAllPorts("O");
            listLocation.getLocationList().getLocations().addAll(pList.getLocationList().getLocations());
            listLocation.getLocationList().getLocations().addAll(oList.getLocationList().getLocations());
            
            System.out.println("From Reader >> "+listLocation.getLocationList().getLocations().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listLocation;
    }

}

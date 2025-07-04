package com.rcyc.batchsystem.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.model.elastic.Itenarary;
import com.rcyc.batchsystem.model.elastic.Itinerary;
import com.rcyc.batchsystem.model.resco.ResListItenarary;
import com.rcyc.batchsystem.service.RescoClient;

public class ItinararyReader implements ItemReader<DefaultPayLoad<Itinerary, Object, Itinerary>> {

    @Autowired
    private RescoClient rescoClient;
    private boolean alreadyRead = false;

    @Override
    public DefaultPayLoad<Itinerary, Object, Itinerary> read()
            throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        try {
            if (alreadyRead)
                return null;
            DefaultPayLoad<Itinerary, Object, Itinerary> itenararyPayLoad = new DefaultPayLoad<>();
            itenararyPayLoad.setReader(getItenararyFromResco());
            alreadyRead = true;
            return itenararyPayLoad;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ResListItenarary getItenararyFromResco() {
        // Stub: implement actual call to RescoClient
        // collect both Ptype and O Type Prt data
        // collect all voyages
        // collect itinerary data based on the voyage
        return new ResListItenarary();
    }
} 
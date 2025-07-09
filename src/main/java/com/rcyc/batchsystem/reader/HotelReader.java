package com.rcyc.batchsystem.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rcyc.batchsystem.model.elastic.Hotel;
import com.rcyc.batchsystem.model.job.DefaultPayLoad;
import com.rcyc.batchsystem.model.resco.ResListEvent;
import com.rcyc.batchsystem.model.resco.ReqListEvent;
import com.rcyc.batchsystem.model.resco.User;
import com.rcyc.batchsystem.model.resco.Facility;
import com.rcyc.batchsystem.model.resco.Event;
import com.rcyc.batchsystem.model.resco.Availability;
import com.rcyc.batchsystem.service.RescoClient;

@Component
public class HotelReader implements ItemReader<DefaultPayLoad<Hotel, Object, Hotel>> {
    @Autowired
    private RescoClient rescoClient;
    private boolean alreadyRead = false;
    private int availUnits = 0; // You can make this configurable

    @Override
    public DefaultPayLoad<Hotel, Object, Hotel> read() {
        DefaultPayLoad<Hotel, Object, Hotel> hotelPayLoad = new DefaultPayLoad<>();
        try {
            if (alreadyRead)
                return null;
            hotelPayLoad.setReader(getHotelsFromResco());
            alreadyRead = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hotelPayLoad;
    }

    private ResListEvent getHotelsFromResco() {
        ReqListEvent req = new ReqListEvent();
        req.setUser(new User("webapiprod1", "theGr8tw1de0pen#305"));  
        Facility facility = new Facility();
        facility.setType("H");
        req.setFacility(facility);
        Event event = new Event();
        event.setDisabled(1);
        req.setEvent(event);
        Availability availability = new Availability(availUnits);
        req.setAvailability(availability);
        return rescoClient.getHotels(req);
    }
} 
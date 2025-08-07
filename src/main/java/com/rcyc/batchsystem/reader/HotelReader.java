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
import com.rcyc.batchsystem.service.AuditService;
import com.rcyc.batchsystem.service.RescoClient;
import com.rcyc.batchsystem.service.ScheduledJobService;
 
public class HotelReader implements ItemReader<DefaultPayLoad<Hotel, Object, Hotel>> {

    private RescoClient rescoClient; 
    private int availUnits = 0;
    private Long jobId;
    private ScheduledJobService scheduledJobService;
    private AuditService auditService;

    public HotelReader(RescoClient rescoClient, AuditService auditService, ScheduledJobService scheduledJobService,
            Long jobId) {
        this.rescoClient = rescoClient;
        this.auditService = auditService;
        this.scheduledJobService = scheduledJobService;
        this.jobId = jobId;
    }

    @Override
    public DefaultPayLoad<Hotel, Object, Hotel> read() {
        DefaultPayLoad<Hotel, Object, Hotel> hotelPayLoad = new DefaultPayLoad<>();
        try {
            boolean flag = scheduledJobService.isJobAvailableForExecution(jobId, auditService);
            if (!flag) {
                return null;
            }
            hotelPayLoad.setReader(getHotelsFromResco()); 
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
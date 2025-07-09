package com.rcyc.batchsystem.model.resco;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ReqListEvent")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReqListEvent {
    @XmlElement(name = "User")
    private User user;

    @XmlElement(name = "Facility")
    private Facility facility;

    @XmlElement(name = "Event")
    private Event event;

    @XmlElement(name = "Availability")
    private Availability availability;

    public ReqListEvent() {}

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Facility getFacility() {
        return facility;
    }
    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Event getEvent() {
        return event;
    }
    public void setEvent(Event event) {
        this.event = event;
    }

    public Availability getAvailability() {
        return availability;
    }
    public void setAvailability(Availability availability) {
        this.availability = availability;
    }
} 
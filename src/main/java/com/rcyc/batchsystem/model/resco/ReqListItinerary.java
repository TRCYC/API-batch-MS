package com.rcyc.batchsystem.model.resco;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement; 

@XmlRootElement(name = "ReqListItinerary")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReqListItinerary {
    @XmlElement(name = "User")
    private User user;

    @XmlElement(name = "Itinerary")
    private Itinerary itinerary;

    public ReqListItinerary() {}

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }
    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }
} 
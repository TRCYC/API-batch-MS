package com.rcyc.batchsystem.model.resco;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ReqListLocation")
public class ReqListLocation {
    @XmlElement(name = "User")
    private User user;
    @XmlElement(name = "Location")
    private Location location;

    public ReqListLocation() {
    }

    public ReqListLocation(User user, Location location) {
        this.user = user;
        this.location = location;
    }   

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }



    @Override
    public String toString() {
        return "ReqListLocation{" +
                "user=" + user +
                ", location=" + location +
                '}';
    }



}

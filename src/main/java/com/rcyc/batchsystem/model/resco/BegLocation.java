package com.rcyc.batchsystem.model.resco;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name = "BegLocation")
@XmlAccessorType(XmlAccessType.FIELD)
public class BegLocation {

    @XmlElement(name = "BegLocation")
    private String begLocation;

    public String getBegLocation() {
        return begLocation;
    }

    public void setBegLocation(String begLocation) {
        this.begLocation = begLocation;
    }



}

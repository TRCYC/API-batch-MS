package com.rcyc.batchsystem.model.resco;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Event")
@XmlAccessorType(XmlAccessType.FIELD)
public class Event {
    @XmlElement(name="Disabled")
    private int disabled;

    public Event(){}
    
    public Event(int disabled){
        this.disabled = disabled;
    }

    public int getDisabled() {
        return disabled;
    }
    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }
}
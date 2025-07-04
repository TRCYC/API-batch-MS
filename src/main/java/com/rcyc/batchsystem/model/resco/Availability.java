package com.rcyc.batchsystem.model.resco;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Availability")
@XmlAccessorType(XmlAccessType.FIELD)
public class Availability {
    @XmlElement(name = "AvailUnits")
    private int availUnits;

    public Availability(){}
    public Availability(int availUnits){this.availUnits = availUnits;}

    public int getAvailUnits() {
        return availUnits;
    }
    public void setAvailUnits(int availUnits) {
        this.availUnits = availUnits;
    }
}
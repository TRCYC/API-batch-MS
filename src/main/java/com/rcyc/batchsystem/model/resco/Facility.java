package com.rcyc.batchsystem.model.resco;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Facility")
@XmlAccessorType(XmlAccessType.FIELD)
public class Facility {
    @XmlElement(name = "Type")
    private String type;

    public Facility(){}
    public Facility(String type){this.type=type;}
    // Getter and Setter
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}





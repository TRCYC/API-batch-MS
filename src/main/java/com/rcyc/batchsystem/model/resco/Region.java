package com.rcyc.batchsystem.model.resco;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Region")
@XmlAccessorType(XmlAccessType.FIELD)
public class Region {
    @XmlElement(name = "Code")
    private String code;
    public Region() {}
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
} 

package com.rcyc.batchsystem.model.resco;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlAccessorType(XmlAccessType.FIELD)
public class Category {
    @XmlElement(name = "EventId")
    private String eventId;
    @XmlElement(name = "CategoryId")
    private String categoryId;
    @XmlElement(name = "Code")
    private String code;
    @XmlElement(name = "Name")
    private String name;
    @XmlElement(name = "Sort")
    private String sort;
    @XmlElement(name = "Type")
    private String type;
    @XmlElement(name = "RegularCapacity")
    private int regularCapacity;
    @XmlElement(name = "AvailUnits")
    private int availUnits;
    @XmlElementWrapper(name = "RateList")
    @XmlElement(name = "Rate")
    private java.util.List<Rate> rateList;

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSort() { return sort; }
    public void setSort(String sort) { this.sort = sort; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public int getRegularCapacity() { return regularCapacity; }
    public void setRegularCapacity(int regularCapacity) { this.regularCapacity = regularCapacity; }
    public int getAvailUnits() { return availUnits; }
    public void setAvailUnits(int availUnits) { this.availUnits = availUnits; }
    public java.util.List<Rate> getRateList() { return rateList; }
    public void setRateList(java.util.List<Rate> rateList) { this.rateList = rateList; }

 
} 
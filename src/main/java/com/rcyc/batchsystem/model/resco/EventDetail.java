package com.rcyc.batchsystem.model.resco;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "EventDetail")
@XmlAccessorType(XmlAccessType.FIELD)
public class EventDetail {
    @XmlElement(name = "EventId")
    private int eventId;
    @XmlElement(name = "ExternalId")
    private String externalId;
    @XmlElement(name = "Code")
    private String code;
    @XmlElement(name = "Name")
    private String name;
    @XmlElement(name = "Sort")
    private String sort;
    @XmlElement(name = "BegDate")
    private String begDate;
    @XmlElement(name = "EndDate")
    private String endDate;
    @XmlElement(name = "BegTimeZone")
    private double begTimeZone;
    @XmlElement(name = "EndTimeZone")
    private double endTimeZone;
    @XmlElement(name = "Duration")
    private int duration;
    @XmlElement(name = "BegLocation")
    private String begLocation;
    @XmlElement(name = "EndLocation")
    private String endLocation;
    @XmlElement(name = "SalePeriod")
    private String salePeriod;
    @XmlElement(name = "InfoPrice")
    private double infoPrice;
    @XmlElement(name = "Flex01")
    private String flex01;
    @XmlElement(name = "Flex02")
    private String flex02;
    @XmlElement(name = "FacilityId")
    private int facilityId;
    @XmlElement(name = "FacilityCode")
    private String facilityCode;
    @XmlElement(name = "FacilityName")
    private String facilityName;
    @XmlElement(name = "ProviderId")
    private int providerId;
    @XmlElement(name = "ProviderCode")
    private String providerCode;
    @XmlElement(name = "ProviderName")
    private String providerName;
    @XmlElement(name = "OperatorId")
    private int operatorId;
    @XmlElement(name = "Enabled")
    private int enabled;
    @XmlElement(name = "ModifyDate")
    private String modifyDate;
    @XmlElementWrapper(name = "RegionList")
    @XmlElement(name = "Region")
    private List<Region> regionList;
    @XmlElement(name = "Comments")
    private String comments; 
    public EventDetail() {}

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getBegDate() {
        return begDate;
    }

    public void setBegDate(String begDate) {
        this.begDate = begDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getBegTimeZone() {
        return begTimeZone;
    }

    public void setBegTimeZone(double begTimeZone) {
        this.begTimeZone = begTimeZone;
    }

    public double getEndTimeZone() {
        return endTimeZone;
    }

    public void setEndTimeZone(double endTimeZone) {
        this.endTimeZone = endTimeZone;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getBegLocation() {
        return begLocation;
    }

    public void setBegLocation(String begLocation) {
        this.begLocation = begLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public String getSalePeriod() {
        return salePeriod;
    }

    public void setSalePeriod(String salePeriod) {
        this.salePeriod = salePeriod;
    }

    public double getInfoPrice() {
        return infoPrice;
    }

    public void setInfoPrice(double infoPrice) {
        this.infoPrice = infoPrice;
    }

    public String getFlex01() {
        return flex01;
    }

    public void setFlex01(String flex01) {
        this.flex01 = flex01;
    }

    public String getFlex02() {
        return flex02;
    }

    public void setFlex02(String flex02) {
        this.flex02 = flex02;
    }

    public int getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(int facilityId) {
        this.facilityId = facilityId;
    }

    public String getFacilityCode() {
        return facilityCode;
    }

    public void setFacilityCode(String facilityCode) {
        this.facilityCode = facilityCode;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public List<Region> getRegionList() {
        return regionList;
    }

    public void setRegionList(List<Region> regionList) {
        this.regionList = regionList;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    } 

    
}

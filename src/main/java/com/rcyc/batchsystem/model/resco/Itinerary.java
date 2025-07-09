package com.rcyc.batchsystem.model.resco;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Itinerary")
@XmlAccessorType(XmlAccessType.FIELD)
public class Itinerary {
    @XmlElement(name = "ItineraryId")
    private Long itineraryId;
    @XmlElement(name = "FacilityId")
    private Long facilityId;
    @XmlElement(name = "EventId")
    private Long eventId;
    @XmlElement(name = "Type")
    private String type;
    @XmlElement(name = "Location")
    private String location;
    @XmlElement(name = "DepDate")
    private String depDate;
    @XmlElement(name = "ArrDate")
    private String arrDate;
    @XmlElement(name = "DepTimeZone")
    private Double depTimeZone;
    @XmlElement(name = "ArrTimeZone")
    private Double arrTimeZone;
    @XmlElement(name = "DepPosition")
    private String depPosition;
    @XmlElement(name = "ArrPosition")
    private String arrPosition;
    @XmlElement(name = "Enabled")
    private Integer enabled;
    @XmlElement(name = "ModifyDate")
    private String modifyDate;

    public Itinerary() {}
    public Itinerary(Long eventId) {this.eventId =eventId;}

    // Getters and setters for all fields
    public Long getItineraryId() { return itineraryId; }
    public void setItineraryId(Long itineraryId) { this.itineraryId = itineraryId; }
    public Long getFacilityId() { return facilityId; }
    public void setFacilityId(Long facilityId) { this.facilityId = facilityId; }
    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getDepDate() { return depDate; }
    public void setDepDate(String depDate) { this.depDate = depDate; }
    public String getArrDate() { return arrDate; }
    public void setArrDate(String arrDate) { this.arrDate = arrDate; }
    public Double getDepTimeZone() { return depTimeZone; }
    public void setDepTimeZone(Double depTimeZone) { this.depTimeZone = depTimeZone; }
    public Double getArrTimeZone() { return arrTimeZone; }
    public void setArrTimeZone(Double arrTimeZone) { this.arrTimeZone = arrTimeZone; }
    public String getDepPosition() { return depPosition; }
    public void setDepPosition(String depPosition) { this.depPosition = depPosition; }
    public String getArrPosition() { return arrPosition; }
    public void setArrPosition(String arrPosition) { this.arrPosition = arrPosition; }
    public Integer getEnabled() { return enabled; }
    public void setEnabled(Integer enabled) { this.enabled = enabled; }
    public String getModifyDate() { return modifyDate; }
    public void setModifyDate(String modifyDate) { this.modifyDate = modifyDate; }
}
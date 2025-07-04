package com.rcyc.batchsystem.model.resco;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Location")
public class Location {
    @XmlElement(name = "LocationId")
    private Long locationId;
    @XmlElement(name = "Code")
    private String code;
    @XmlElement(name = "Name")
    private String name;
    @XmlElement(name = "BaseMessage")
    private String baseMessage;
    @XmlElement(name = "Type")
    private String type;
    @XmlElement(name = "Region")
    private String region;
    @XmlElement(name = "Country")
    private String country;
    @XmlElement(name = "Latitude")
    private String latitude;
    @XmlElement(name = "Longitude")
    private String longitude;
    @XmlElement(name = "TimeZone")
    private String timeZone;
    @XmlElement(name = "Enabled")
    private int enabled;

    public Location(){}

    public Location(int enabled,String type){this.type=type;this.enabled=enabled;}

    public Long getLocationId() { return locationId; }
    public void setLocationId(Long locationId) { this.locationId = locationId; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBaseMessage() { return baseMessage; }
    public void setBaseMessage(String baseMessage) { this.baseMessage = baseMessage; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getLatitude() { return latitude; }
    public void setLatitude(String latitude) { this.latitude = latitude; }
    public String getLongitude() { return longitude; }
    public void setLongitude(String longitude) { this.longitude = longitude; }
    public String getTimeZone() { return timeZone; }
    public void setTimeZone(String timeZone) { this.timeZone = timeZone; }
    public int getEnabled() { return enabled; }
    public void setEnabled(int enabled) { this.enabled = enabled; }
}

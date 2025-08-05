package com.rcyc.batchsystem.model.resco;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ResListLocation")
public class ResListLocation {
    @XmlElement(name = "LocationList")
    private LocationList locationList;  
    @XmlElement(name = "Result")
    private Result result;

    public ResListLocation() {
        locationList = new LocationList();
    }

    public ResListLocation(LocationList locationList, Result result) {
        this.locationList = locationList;
        this.result = result;
    }

    public LocationList getLocationList() {
        return locationList;
    }

    public Result getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "ResListLocation [locationList=" + locationList + ", result=" + result + "]";
    }

    
}
  
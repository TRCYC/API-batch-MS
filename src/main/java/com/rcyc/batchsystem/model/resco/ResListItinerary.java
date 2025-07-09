package com.rcyc.batchsystem.model.resco;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "ResListItinerary")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResListItinerary {
    @XmlElement(name = "Result")
    private Result result;

    @XmlElementWrapper(name = "ItineraryList")
    @XmlElement(name = "Itinerary")
    private List<Itinerary> itineraryList;

    public ResListItinerary() {}

    public Result getResult() {
        return result;
    }
    public void setResult(Result result) {
        this.result = result;
    }

    public List<Itinerary> getItineraryList() {
        return itineraryList;
    }
    public void setItineraryList(List<Itinerary> itineraryList) {
        this.itineraryList = itineraryList;
    }
} 
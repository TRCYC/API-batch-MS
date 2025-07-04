package com.rcyc.batchsystem.model.resco;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ResListEvent")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResListEvent {
    @XmlElement(name = "Result")
    private Result result;

    @XmlElementWrapper(name = "EventList")
    @XmlElement(name = "Event")
    private List<EventDetail> eventList;

    public ResListEvent() {}

    public Result getResult() {
        return result;
    }
    public void setResult(Result result) {
        this.result = result;
    }

    public List<EventDetail> getEventList() {
        return eventList;
    }
    public void setEventList(List<EventDetail> eventList) {
        this.eventList = eventList;
    }
}
 
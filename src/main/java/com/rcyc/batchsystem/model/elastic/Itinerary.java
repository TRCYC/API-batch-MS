package com.rcyc.batchsystem.model.elastic;

import java.util.List;

public class Itinerary {
    private int totalItineraryCount;
    private String facilityId;
    private String arrDateStr;
    private long arrivalTime;
    private int totalCount;
    private String createdDate;
    private String voyageNumber;
    private String portCode;
    private String comment;
    private int sequenceNumber;
    private long date;
    private String eventId;
    private int depTimeZone;
    private int currentItineraryCount;
    private List<ItineraryProcessFinished> itinerary_process_finished;
    private int id;
    private String enabled;
    private String type;
    private String depDateStr;
    private String portName;
    private int currentCount;
    private int arrTimeZone;
    private String dayType;
    private String depPosition;
    private String day;
    private String arrPosition;

    public int getTotalItineraryCount() { return totalItineraryCount; }
    public void setTotalItineraryCount(int totalItineraryCount) { this.totalItineraryCount = totalItineraryCount; }
    public String getFacilityId() { return facilityId; }
    public void setFacilityId(String facilityId) { this.facilityId = facilityId; }
    public String getArrDateStr() { return arrDateStr; }
    public void setArrDateStr(String arrDateStr) { this.arrDateStr = arrDateStr; }
    public long getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(long arrivalTime) { this.arrivalTime = arrivalTime; }
    public int getTotalCount() { return totalCount; }
    public void setTotalCount(int totalCount) { this.totalCount = totalCount; }
    public String getCreatedDate() { return createdDate; }
    public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }
    public String getVoyageNumber() { return voyageNumber; }
    public void setVoyageNumber(String voyageNumber) { this.voyageNumber = voyageNumber; }
    public String getPortCode() { return portCode; }
    public void setPortCode(String portCode) { this.portCode = portCode; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public int getSequenceNumber() { return sequenceNumber; }
    public void setSequenceNumber(int sequenceNumber) { this.sequenceNumber = sequenceNumber; }
    public long getDate() { return date; }
    public void setDate(long date) { this.date = date; }
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public int getDepTimeZone() { return depTimeZone; }
    public void setDepTimeZone(int depTimeZone) { this.depTimeZone = depTimeZone; }
    public int getCurrentItineraryCount() { return currentItineraryCount; }
    public void setCurrentItineraryCount(int currentItineraryCount) { this.currentItineraryCount = currentItineraryCount; }
    public List<ItineraryProcessFinished> getItinerary_process_finished() { return itinerary_process_finished; }
    public void setItinerary_process_finished(List<ItineraryProcessFinished> itinerary_process_finished) { this.itinerary_process_finished = itinerary_process_finished; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getEnabled() { return enabled; }
    public void setEnabled(String enabled) { this.enabled = enabled; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getDepDateStr() { return depDateStr; }
    public void setDepDateStr(String depDateStr) { this.depDateStr = depDateStr; }
    public String getPortName() { return portName; }
    public void setPortName(String portName) { this.portName = portName; }
    public int getCurrentCount() { return currentCount; }
    public void setCurrentCount(int currentCount) { this.currentCount = currentCount; }
    public int getArrTimeZone() { return arrTimeZone; }
    public void setArrTimeZone(int arrTimeZone) { this.arrTimeZone = arrTimeZone; }
    public String getDayType() { return dayType; }
    public void setDayType(String dayType) { this.dayType = dayType; }
    public String getDepPosition() { return depPosition; }
    public void setDepPosition(String depPosition) { this.depPosition = depPosition; }
    public String getDay() { return day; }
    public void setDay(String day) { this.day = day; }
    public String getArrPosition() { return arrPosition; }
    public void setArrPosition(String arrPosition) { this.arrPosition = arrPosition; }

    public static class ItineraryProcessFinished {
        private int audit_id;
        public int getAudit_id() { return audit_id; }
        public void setAudit_id(int audit_id) { this.audit_id = audit_id; }
    }
} 
package com.rcyc.batchsystem.model.elastic;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.rcyc.batchsystem.model.elastic.VoyageLogstashFinish;
import com.rcyc.batchsystem.model.elastic.FeedStartDate;
import com.rcyc.batchsystem.model.elastic.PortData;

public class Voyage {
    private String portCode;
    private String yachtName;
    private Set<String> portCodes;
    private int nights;
    private long voyageEndDate;
    private String voyageType;
    private String voyageName;
    private String voyageEmbarkPortCode;
    private List<VoyageLogstashFinish> voyage_logstash_finish;
    private String voyageDisembarkPortCode;
    private List<FeedStartDate> feed_start_date;
    private List<PortData> portData;
    private String voyageStartDOW;
    private String voyageEmbarkPort;
    private String voyageId;
    private String voyageRegionExpansion;
    private String voyageregioncode;
    private int suiteAvailability;
    private long voyageStartDate;
    private String createdDate;
    private int id;
    private int startingPrice;
    private String voyageRegion;
    private String voyageUrlPath;
    private String departureYearMonth;
    private Map<String, Integer> startingPriceMap;
    private String voyageDisembarkPort;
    private Map<String, Integer> portFeeMap;
    private String voyageEndDOW;
    private int yachtId;
    private String voyageDisembarkRegion;
    private String voyageEmbarkRegion;
    private Set<String> ports;
    private int eventId;

    // Getters and setters for all fields
    public String getPortCode() { return portCode; }
    public void setPortCode(String portCode) { this.portCode = portCode; }
    public String getYachtName() { return yachtName; }
    public void setYachtName(String yachtName) { this.yachtName = yachtName; }
    public Set<String> getPortCodes() { return portCodes; }
    public void setPortCodes(Set<String> portCodes) { this.portCodes = portCodes; }
    public int getNights() { return nights; }
    public void setNights(int nights) { this.nights = nights; }
    public long getVoyageEndDate() { return voyageEndDate; }
    public void setVoyageEndDate(long voyageEndDate) { this.voyageEndDate = voyageEndDate; }
    public String getVoyageType() { return voyageType; }
    public void setVoyageType(String voyageType) { this.voyageType = voyageType; }
    public String getVoyageName() { return voyageName; }
    public void setVoyageName(String voyageName) { this.voyageName = voyageName; }
    public String getVoyageEmbarkPortCode() { return voyageEmbarkPortCode; }
    public void setVoyageEmbarkPortCode(String voyageEmbarkPortCode) { this.voyageEmbarkPortCode = voyageEmbarkPortCode; }
    public List<VoyageLogstashFinish> getVoyage_logstash_finish() { return voyage_logstash_finish; }
    public void setVoyage_logstash_finish(List<VoyageLogstashFinish> voyage_logstash_finish) { this.voyage_logstash_finish = voyage_logstash_finish; }
    public String getVoyageDisembarkPortCode() { return voyageDisembarkPortCode; }
    public void setVoyageDisembarkPortCode(String voyageDisembarkPortCode) { this.voyageDisembarkPortCode = voyageDisembarkPortCode; }
    public List<FeedStartDate> getFeed_start_date() { return feed_start_date; }
    public void setFeed_start_date(List<FeedStartDate> feed_start_date) { this.feed_start_date = feed_start_date; }
    public List<PortData> getPortData() { return portData; }
    public void setPortData(List<PortData> portData) { this.portData = portData; }
    public String getVoyageStartDOW() { return voyageStartDOW; }
    public void setVoyageStartDOW(String voyageStartDOW) { this.voyageStartDOW = voyageStartDOW; }
    public String getVoyageEmbarkPort() { return voyageEmbarkPort; }
    public void setVoyageEmbarkPort(String voyageEmbarkPort) { this.voyageEmbarkPort = voyageEmbarkPort; }
    public String getVoyageId() { return voyageId; }
    public void setVoyageId(String voyageId) { this.voyageId = voyageId; }
    public String getVoyageRegionExpansion() { return voyageRegionExpansion; }
    public void setVoyageRegionExpansion(String voyageRegionExpansion) { this.voyageRegionExpansion = voyageRegionExpansion; }
    public String getVoyageregioncode() { return voyageregioncode; }
    public void setVoyageregioncode(String voyageregioncode) { this.voyageregioncode = voyageregioncode; }
    public int getSuiteAvailability() { return suiteAvailability; }
    public void setSuiteAvailability(int suiteAvailability) { this.suiteAvailability = suiteAvailability; }
    public long getVoyageStartDate() { return voyageStartDate; }
    public void setVoyageStartDate(long voyageStartDate) { this.voyageStartDate = voyageStartDate; }
    public String getCreatedDate() { return createdDate; }
    public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getStartingPrice() { return startingPrice; }
    public void setStartingPrice(int startingPrice) { this.startingPrice = startingPrice; }
    public String getVoyageRegion() { return voyageRegion; }
    public void setVoyageRegion(String voyageRegion) { this.voyageRegion = voyageRegion; }
    public String getVoyageUrlPath() { return voyageUrlPath; }
    public void setVoyageUrlPath(String voyageUrlPath) { this.voyageUrlPath = voyageUrlPath; }
    public String getDepartureYearMonth() { return departureYearMonth; }
    public void setDepartureYearMonth(String departureYearMonth) { this.departureYearMonth = departureYearMonth; }
    public Map<String, Integer> getStartingPriceMap() { return startingPriceMap; }
    public void setStartingPriceMap(Map<String, Integer> startingPriceMap) { this.startingPriceMap = startingPriceMap; }
    public String getVoyageDisembarkPort() { return voyageDisembarkPort; }
    public void setVoyageDisembarkPort(String voyageDisembarkPort) { this.voyageDisembarkPort = voyageDisembarkPort; }
    public Map<String, Integer> getPortFeeMap() { return portFeeMap; }
    public void setPortFeeMap(Map<String, Integer> portFeeMap) { this.portFeeMap = portFeeMap; }
    public String getVoyageEndDOW() { return voyageEndDOW; }
    public void setVoyageEndDOW(String voyageEndDOW) { this.voyageEndDOW = voyageEndDOW; }
    public int getYachtId() { return yachtId; }
    public void setYachtId(int yachtId) { this.yachtId = yachtId; }
    public String getVoyageDisembarkRegion() { return voyageDisembarkRegion; }
    public void setVoyageDisembarkRegion(String voyageDisembarkRegion) { this.voyageDisembarkRegion = voyageDisembarkRegion; }
    public String getVoyageEmbarkRegion() { return voyageEmbarkRegion; }
    public void setVoyageEmbarkRegion(String voyageEmbarkRegion) { this.voyageEmbarkRegion = voyageEmbarkRegion; }
    public Set<String> getPorts() { return ports; }
    public void setPorts(Set<String> ports) { this.ports = ports; }
    public int getEventId() {
        return eventId;
    }
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
    
} 
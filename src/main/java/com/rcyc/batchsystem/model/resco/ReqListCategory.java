package com.rcyc.batchsystem.model.resco;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ReqListCategory")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReqListCategory {
    @XmlElement(name = "User")
    private User user;
    @XmlElement(name = "Agency")
    private Agency agency;
    @XmlElement(name = "Category")
    private Category category;
    @XmlElement(name = "Rate")
    private Rate rate;

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Agency getAgency() { return agency; }
    public void setAgency(Agency agency) { this.agency = agency; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public Rate getRate() { return rate; }
    public void setRate(Rate rate) { this.rate = rate; }

    public static class Agency {
        @XmlElement(name = "AgentId")
        private String agentId;
        public String getAgentId() { return agentId; }
        public void setAgentId(String agentId) { this.agentId = agentId; }
    }
    public static class Category {
        @XmlElement(name = "EventId")
        private String eventId;
        public String getEventId() { return eventId; }
        public void setEventId(String eventId) { this.eventId = eventId; }
    }
    public static class Rate {
        @XmlElement(name = "Currency")
        private String currency;
        @XmlElement(name = "Surcharges")
        private int surcharges;
        public String getCurrency() { return currency; }
        public void setCurrency(String currency) { this.currency = currency; }
        public int getSurcharges() { return surcharges; }
        public void setSurcharges(int surcharges) { this.surcharges = surcharges; }
    }
} 
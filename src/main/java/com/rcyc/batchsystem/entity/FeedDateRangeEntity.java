package com.rcyc.batchsystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rcyc_feed_date_range")
public class FeedDateRangeEntity {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "feed")
    private String feed;

    @Column(name = "type")
    private String type;

    @Column(name = "start_at")
    private String startAt;

    public FeedDateRangeEntity() {}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getFeed() {
        return feed;
    }
    public void setFeed(String feed) {
        this.feed = feed;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getStartAt() {
        return startAt;
    }
    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public  boolean isBegDateOnOrAfterStartAt(String begDate) {
        java.time.LocalDate beg = java.time.LocalDate.parse(begDate);
        java.time.LocalDate start = java.time.LocalDate.parse(startAt);
        return !beg.isBefore(start); // true if begDate is equal or after startAt
    }
} 
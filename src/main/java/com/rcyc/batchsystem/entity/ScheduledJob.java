package com.rcyc.batchsystem.entity;

import java.time.LocalDateTime;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "rcyc_scheduled_jobs")
public class ScheduledJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long externalJobId;
    private Long schedulerId;
    private Integer jobStatus;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime lastUpdatedAt;

    public ScheduledJob() {
    }

    public ScheduledJob(Long id, Long externalJobId, Long schedulerId, Integer jobStatus, LocalDateTime createdAt,
            LocalDateTime lastUpdatedAt) {
        this.id = id;
        this.externalJobId = externalJobId;
        this.schedulerId = schedulerId;
        this.jobStatus = jobStatus;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExternalJobId() {
        return externalJobId;
    }

    public void setExternalJobId(Long externalJobId) {
        this.externalJobId = externalJobId;
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Long getSchedulerId() {
        return schedulerId;
    }

    public void setSchedulerId(Long schedulerId) {
        this.schedulerId = schedulerId;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    @PreUpdate
    public void updateTimestamp() {
        this.lastUpdatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "ScheduledJob [id=" + id + ", externalJobId=" + externalJobId + ", schedulerId=" + schedulerId
                + ", jobStatus=" + jobStatus + ", createdAt=" + createdAt + ", lastUpdatedAt=" + lastUpdatedAt + "]";
    }

}

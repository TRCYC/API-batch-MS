package com.rcyc.batchsystem.entity;


import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "rcyc_scheduler_history")
public class RcycSchedulerHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Integer jobId;

    @Column(name = "scheduler_id")
    private Integer schedulerId;

    @Column(name = "is_manual")
    private Boolean isManual;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "updated_time")
    private LocalDateTime updatedTime;

    @Column(name = "status")
    private String status;

    // Getters and Setters

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getSchedulerId() {
        return schedulerId;
    }

    public void setSchedulerId(Integer schedulerId) {
        this.schedulerId = schedulerId;
    }

    public Boolean getIsManual() {
        return isManual;
    }

    public void setIsManual(Boolean isManual) {
        this.isManual = isManual;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

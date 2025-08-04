package com.rcyc.batchsystem.entity;


import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "rcyc_scheduler_queue")
public class RcycSchedulerQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_queue")
    private Integer idQueue;

    @Column(name = "scheduler_id")
    private Integer schedulerId;

    @Column(name = "process", length = 45)
    private String process;

    @Column(name = "is_running")
    private Boolean isRunning = false;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    // Constructors
    public RcycSchedulerQueue() {}

    // Getters and setters

    public Integer getIdQueue() {
        return idQueue;
    }

    public void setIdQueue(Integer idQueue) {
        this.idQueue = idQueue;
    }

    public Integer getSchedulerId() {
        return schedulerId;
    }

    public void setSchedulerId(Integer schedulerId) {
        this.schedulerId = schedulerId;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public Boolean getIsRunning() {
        return isRunning;
    }

    public void setIsRunning(Boolean isRunning) {
        this.isRunning = isRunning;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}

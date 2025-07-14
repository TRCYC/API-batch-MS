package com.rcyc.batchsystem.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rcyc_audit")
public class RcycAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_id")
    private Long auditId;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "process_name")
    private String processName;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "response_time")
    private LocalDateTime responseTime;

    @Column(name = "updated_time")
    private LocalDateTime updatedTime;

    @Column(name = "description")
    private String description;

    // Getters and setters
    public Long getAuditId() { return auditId; }
    public void setAuditId(Long auditId) { this.auditId = auditId; }
    public Long getJobId() { return jobId; }
    public void setJobId(Long jobId) { this.jobId = jobId; }
    public String getProcessName() { return processName; }
    public void setProcessName(String processName) { this.processName = processName; }
    public LocalDateTime getCreatedTime() { return createdTime; }
    public void setCreatedTime(LocalDateTime createdTime) { this.createdTime = createdTime; }
    public LocalDateTime getResponseTime() { return responseTime; }
    public void setResponseTime(LocalDateTime responseTime) { this.responseTime = responseTime; }
    public LocalDateTime getUpdatedTime() { return updatedTime; }
    public void setUpdatedTime(LocalDateTime updatedTime) { this.updatedTime = updatedTime; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
} 
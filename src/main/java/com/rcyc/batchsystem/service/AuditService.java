package com.rcyc.batchsystem.service;

import com.rcyc.batchsystem.entity.RcycAudit;
import com.rcyc.batchsystem.repository.RcycAuditRepository;
import com.rcyc.batchsystem.repository.ScheduledJobsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuditService {
    @Autowired
    private RcycAuditRepository auditRepository;

    @Autowired
    private ScheduledJobsRepository scheduledJobsRepository;

    public RcycAudit logAudit(Long jobId, String processName, LocalDateTime createdTime, LocalDateTime responseTime,
            LocalDateTime updatedTime, String description) {
        RcycAudit audit = new RcycAudit();
        audit.setJobId(jobId);
        audit.setProcessName(getSchedulerName(jobId));
        audit.setCreatedTime(createdTime);
        audit.setResponseTime(responseTime);
        audit.setUpdatedTime(updatedTime);
        audit.setDescription(description);
        return auditRepository.save(audit);
    }

    public RcycAudit logAudit(Long jobId, String processName, String description) {
        RcycAudit audit = new RcycAudit();
        audit.setJobId(jobId);
        audit.setProcessName(getSchedulerName(jobId));
        audit.setCreatedTime(LocalDateTime.now());
        audit.setResponseTime(LocalDateTime.now());
        audit.setUpdatedTime(LocalDateTime.now());
        audit.setDescription(description);
        return auditRepository.save(audit);
    }

    private String getSchedulerName(Long jobId) {
        try {
            Optional<String> schedulerName = scheduledJobsRepository.findSchedulerNameByExternalJobId(jobId);
            return schedulerName.isPresent() ? schedulerName.get() : "feed_type";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
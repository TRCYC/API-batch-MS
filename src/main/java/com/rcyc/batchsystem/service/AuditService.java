package com.rcyc.batchsystem.service;

import com.rcyc.batchsystem.entity.RcycAudit;
import com.rcyc.batchsystem.repository.RcycAuditRepository;
import com.rcyc.batchsystem.repository.ScheduledJobsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuditService {
    @Autowired
    private RcycAuditRepository auditRepository;

    @Autowired
    private ScheduledJobsRepository scheduledJobsRepository;

    private final ConcurrentHashMap<Long, String> schedulerNameCache = new ConcurrentHashMap<>();

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
        // Check cache first
        if (schedulerNameCache.containsKey(jobId)) {
            return schedulerNameCache.get(jobId);
        }
        try {
            Optional<String> schedulerName = scheduledJobsRepository.findSchedulerNameByExternalJobId(jobId);
            String name = schedulerName.isPresent() ? schedulerName.get() : "feed_type";
            schedulerNameCache.put(jobId, name);
            return name;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
package com.rcyc.batchsystem.service;

import com.rcyc.batchsystem.entity.RcycAudit;
import com.rcyc.batchsystem.repository.RcycAuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditService {
    @Autowired
    private RcycAuditRepository auditRepository;

    public RcycAudit logAudit(Long jobId, String processName, LocalDateTime createdTime, LocalDateTime responseTime, LocalDateTime updatedTime, String description) {
        RcycAudit audit = new RcycAudit();
        audit.setJobId(jobId);
        audit.setProcessName(processName);
        audit.setCreatedTime(createdTime);
        audit.setResponseTime(responseTime);
        audit.setUpdatedTime(updatedTime);
        audit.setDescription(description);
        return auditRepository.save(audit);
    }

    public RcycAudit logAudit(Long jobId, String processName,String description) {
        RcycAudit audit = new RcycAudit();
        audit.setJobId(jobId);
        audit.setProcessName(processName);
        audit.setCreatedTime(LocalDateTime.now());
        audit.setResponseTime(LocalDateTime.now());
        audit.setUpdatedTime(LocalDateTime.now());
        audit.setDescription(description);
        return auditRepository.save(audit);
    }
} 
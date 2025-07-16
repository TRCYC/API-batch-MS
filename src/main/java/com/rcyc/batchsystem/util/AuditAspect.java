package com.rcyc.batchsystem.util;

import java.time.LocalDateTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.StepSynchronizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rcyc.batchsystem.service.AuditService;

@Aspect
@Component
public class AuditAspect {

    @Autowired
    private AuditService auditService;

    @Before("execution(* com.rcyc.batchsystem.reader..*.read(..))")
    public void logReader(JoinPoint joinPoint) {
        Long jobId = getCurrentJobId();
        if (jobId != null)
            auditService.logAudit(jobId, "feed_type", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(),
                    "System call reader");
    }

    @Before("execution(* com.rcyc.batchsystem.process..*.*(..))")
    public void logProcessor(JoinPoint joinPoint) {
        Long jobId = getCurrentJobId();
        if (jobId != null)
            auditService.logAudit(jobId, "feed_type", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(),
                    "System call processor");
    }

    // Example for writer
    @Before("execution(* com.rcyc.batchsystem.writer..*.write(..))")
    public void logWriter(JoinPoint joinPoint) {
        Long jobId = getCurrentJobId();
        if (jobId != null)
            auditService.logAudit(jobId, "feed_type", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(),
                    "System call writer");
    }

    @After("execution(* com.rcyc.batchsystem.reader..*.read(..))")
    public void logAferReader(JoinPoint joinPoint) {
        Long jobId = getCurrentJobId();
        if (jobId != null)
            auditService.logAudit(jobId, "feed_type", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(),
                    "Leaving from reader");
    }

    @After("execution(* com.rcyc.batchsystem.process..*.*(..))")
    public void logAfterProcessor(JoinPoint joinPoint) {
        Long jobId = getCurrentJobId();
        if (jobId != null)
            auditService.logAudit(jobId, "feed_type", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(),
                    "Leaving from processor");
    }

    // Example for writer
    @After("execution(* com.rcyc.batchsystem.writer..*.write(..))")
    public void logAfterWriter(JoinPoint joinPoint) {
        Long jobId = getCurrentJobId();
        if (jobId != null)
            auditService.logAudit(jobId, "feed_type", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(),
                    "Leaving from writer");
    }

    // Utility method to get jobId from current StepExecution
    private Long getCurrentJobId() {
        try {
            org.springframework.batch.core.StepExecution stepExecution = org.springframework.batch.core.scope.context.StepSynchronizationManager
                    .getContext().getStepExecution();
            if (stepExecution != null) {
                return stepExecution.getJobParameters().getLong("jobId");
            }
        } catch (Exception e) {
            // Handle or log exception if needed
        }
        return null;
    }
}

package com.rcyc.batchsystem.service;

import java.time.LocalDateTime;
import java.util.List; 
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rcyc.batchsystem.entity.ScheduledJob;
import com.rcyc.batchsystem.repository.ScheduledJobsRepository;
import com.rcyc.batchsystem.util.JobStatus;
 

@Service
public class ScheduledJobService {
private static final Logger logger = LoggerFactory.getLogger(ScheduledJobService.class);
    @Autowired
    private ScheduledJobsRepository scheduledJobsRepository;

    @Transactional(propagation =  Propagation.REQUIRES_NEW)
    public boolean isJobAvailableForExecution(Long jobId, AuditService auditService) {
        try {
            System.out.println(" From ScheduledJobService " + jobId);
            ScheduledJob scheduledJob = scheduledJobsRepository.findByExternalJobId(jobId);
            if (scheduledJob != null && scheduledJob.getId() != null && scheduledJob.getJobStatus().intValue()==JobStatus.PENDING.getCode()) {
                List<ScheduledJob> scheduledJobs = scheduledJobsRepository
                        .findByJobStatusAndSchedulerId(JobStatus.PENDING.getCode(), scheduledJob.getSchedulerId());
                
                if (scheduledJobs.size() > 1) {
                    auditService.logAudit(jobId, null, jobId
                            + ": Cannot execute because another job with the same scheduler type is already in the queue with a 'PENDING' status. Please clear the queue before retrying.");
                    return false;
                } else {
                    logger.info("Going to update job to running status");
                    scheduledJob.setJobStatus(JobStatus.RUNNING.getCode());
                    scheduledJob.setLastUpdatedAt(LocalDateTime.now());
                    scheduledJob = scheduledJobsRepository.save(scheduledJob);
                    System.out.println(scheduledJob.toString());
                    return true;
                }
            }else 
              return false;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}

@FunctionalInterface
interface JobSchedulerQueue {

    public abstract boolean hasMultipleFeed(List<ScheduledJob> jobList);
}

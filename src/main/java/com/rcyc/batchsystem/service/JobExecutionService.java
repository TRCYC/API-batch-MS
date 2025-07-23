package com.rcyc.batchsystem.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class JobExecutionService {

    @Autowired
    private Job excursionVoyageJob;
     @Autowired
    private ElasticService elasticService;

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private AuditService auditService;

    @Async
    public void runExcursionVoyage(Long jobId){
         JobParameters params = new JobParametersBuilder()
                .addLong("jobId", jobId)
                .toJobParameters();
        try {
            jobLauncher.run(excursionVoyageJob, params);
        } catch(Exception e){
            e.printStackTrace();
        }
       
    }
}

package com.rcyc.batchsystem.service;

import java.util.Optional;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rcyc.batchsystem.entity.ScheduledJob;
import com.rcyc.batchsystem.model.StatusDto;
import com.rcyc.batchsystem.repository.ScheduledJobsRepository;
import com.rcyc.batchsystem.util.Constants;
import com.rcyc.batchsystem.util.JobStatus;

@Component
public class RegionStepCallbackListener implements StepExecutionListener {

    @Autowired
    private ExternalApiClient externalApiClient;
    @Autowired
    private ScheduledJobsRepository scheduledJobsRepository;
    @Autowired
    private ElasticService elasticService;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        // Optional: logic before step starts
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        try {
            StatusDto responseDto = new StatusDto();
            Long jobId = stepExecution.getJobParameters().getLong("jobId");
            String schedulerName = getSchedulerName(jobId);
            responseDto.setJobId(jobId);
            responseDto.setProcess(schedulerName);
            if(schedulerName.equalsIgnoreCase(Constants.REGION)){
              Long documentCount =  elasticService.getDocumentCount(Constants.REGION_DEMO);
              responseDto.setCurrentCount(String.valueOf(documentCount));
              responseDto.setStatus(Constants.SUCCESS);
            }
            externalApiClient.callBack(responseDto);  
            System.out.println(" External API callback sent successfully");
        } catch (Exception e) {
            System.err.println(" API callback failed: " + e.getMessage());
            e.printStackTrace();
        }
        return ExitStatus.COMPLETED;
    }

    private String getSchedulerName(Long jobId){
        try{
            ScheduledJob scheduledJob =   scheduledJobsRepository.findByExternalJobId(jobId);
            if(scheduledJob!=null && scheduledJob.getId()!=null && scheduledJob.getJobStatus().intValue()!= JobStatus.PENDING.getCode()){
                Optional<String> schedulerName = scheduledJobsRepository.findSchedulerNameById(scheduledJob.getSchedulerId().intValue());
                if(schedulerName.isPresent())
                    return schedulerName.get();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
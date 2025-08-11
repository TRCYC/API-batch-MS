package com.rcyc.batchsystem.service;

import java.util.Optional;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rcyc.batchsystem.entity.ScheduledJob;
import com.rcyc.batchsystem.model.StatusDto;
import com.rcyc.batchsystem.repository.ScheduledJobsRepository;
import com.rcyc.batchsystem.util.Constants;
import com.rcyc.batchsystem.util.JobStatus;

@Component
public class JobStepCallbackListener implements StepExecutionListener {

    @Autowired
    private ExternalApiClient externalApiClient;
    @Autowired
    private ScheduledJobsRepository scheduledJobsRepository;
    @Autowired
    private ElasticService elasticService;
    @Autowired
    private AuditService auditService;
    @Autowired
    private RegionSwitchProcessor jobSwitchProcessor;

    @Autowired
    @Qualifier("regionSwitch")
    private JobSwitchService regionSwitch;

    @Autowired
    @Qualifier("portSwitch")
    private JobSwitchService portSwitch;


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
            if (schedulerName != null) {
                responseDto.setJobId(jobId);
                responseDto.setProcess(schedulerName);
                responseDto.setStatus(Constants.SUCCESS);
                if (schedulerName != null && schedulerName.equalsIgnoreCase(Constants.REGION)) {
                    responseDto.setCurrentCount(String.valueOf(elasticService.getDocumentCount(Constants.REGION_DEMO)));
                    
                    regionSwitch.doJobSwitch(jobId);
                }
                else if (schedulerName != null && schedulerName.equalsIgnoreCase(Constants.PORT)) {
                    responseDto.setCurrentCount(String.valueOf(elasticService.getDocumentCount(Constants.PORT_DEMO_INDEX)));
                }else if (schedulerName != null && schedulerName.equalsIgnoreCase(Constants.ITINERARY)) {
                    responseDto.setCurrentCount(String.valueOf(elasticService.getDocumentCount(Constants.ITINERARY_DEMO_INDEX)));
                }else if (schedulerName != null && schedulerName.equalsIgnoreCase(Constants.SUITE)) {
                    responseDto.setCurrentCount(String.valueOf(elasticService.getDocumentCount(Constants.SUITE_DEMO_INDEX)));
                }else if (schedulerName != null && schedulerName.equalsIgnoreCase(Constants.HOTEL)) {
                    responseDto.setCurrentCount(String.valueOf(elasticService.getDocumentCount(Constants.HOTEL_DEMO_INDEX)));
                }else if (schedulerName != null && schedulerName.equalsIgnoreCase(Constants.PRICING)) {
                    responseDto.setCurrentCount(String.valueOf(elasticService.getDocumentCount(Constants.PRICING_DEMO_INDEX)));
                }
                 externalApiClient.callBack(responseDto);
                
                //     if (schedulerName != null && schedulerName.equalsIgnoreCase(Constants.PORT)) {
                //     // responseDto.setCurrentCount(String.valueOf(elasticService.getDocumentCount(Constants.REGION_DEMO)));
                //     // responseDto.setStatus(Constants.SUCCESS);
                //     portSwitch.doJobSwitch(jobId);
                // }

              
                // / JobSwitchProcessor
                System.out.println(" External API callback sent successfully");
            }else
              auditService.logAudit(jobId,"anonymous"," No scheduler is related for the job :"+jobId);
        } catch (Exception e) {
            System.err.println(" API callback failed: " + e.getMessage());
            e.printStackTrace();
        }
        return ExitStatus.COMPLETED;
    }

    private String getSchedulerName(Long jobId) {
        try {
            ScheduledJob scheduledJob = scheduledJobsRepository.findByExternalJobId(jobId);
            if (scheduledJob != null && scheduledJob.getId() != null
                    && scheduledJob.getJobStatus().intValue() != JobStatus.PENDING.getCode()) {
                Optional<String> schedulerName = scheduledJobsRepository
                        .findSchedulerNameById(scheduledJob.getSchedulerId().intValue());
                if (schedulerName.isPresent())
                    return schedulerName.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
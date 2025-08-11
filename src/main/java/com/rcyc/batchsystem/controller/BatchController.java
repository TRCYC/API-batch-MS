package com.rcyc.batchsystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rcyc.batchsystem.service.AuditService;
import com.rcyc.batchsystem.service.DelayedJobScheduler;
import com.rcyc.batchsystem.service.ElasticService;
import com.rcyc.batchsystem.service.JobExecutionService;
import com.rcyc.batchsystem.util.Constants;

import java.time.LocalDateTime;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/batch")
public class BatchController {

    @Autowired
    private ElasticService elasticService;

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private AuditService auditService;
    @Autowired
    private  DelayedJobScheduler delayedJobScheduler;
    @Autowired
    private Job regionJob;
    @Autowired
    private Job portJob;
    @Autowired
    private Job itineraryJob;
    @Autowired
    private Job hotelJob;
    @Autowired
    private Job pricingJob;
    @Autowired
    private Job voyageJob;
    @Autowired
    private Job transferJob;
    @Autowired
    private Job suiteJob;
    @Autowired
    private JobExecutionService jobExecutionService;


    @GetMapping("/region")
    public String getMethodName() {
        elasticService.getRegionData();
        return new String("Region");
    }

    @GetMapping("/run-region-job/{jobId}")
    public String runRegionJob(@PathVariable Long jobId) throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("jobId", jobId)
                .toJobParameters();

        delayedJobScheduler.scheduleJob(regionJob, params, Constants.DELAY_IN_MINUTES);
        return "Region job scheduled to run in " +  Constants.DELAY_IN_MINUTES + " minute(s)";
    }

    @GetMapping("/run-port-job/{jobId}")
    public String runPortJob(@PathVariable Long jobId) throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("jobId", jobId)
                .toJobParameters();
        jobLauncher.run(portJob, params);
        return "Port job triggered!";
    }

    @GetMapping("/run-itinerary-job/{jobId}")
    public String itineraryJob(@PathVariable Long jobId) throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("jobId", jobId)
                .toJobParameters();
        jobLauncher.run(itineraryJob, params);
        return "Itinerary job triggered!";
    }

    @GetMapping("/run-hotel-job/{jobId}")
    public String hotelJob(@PathVariable Long jobId) throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("jobId", jobId)
                .toJobParameters();
        jobLauncher.run(hotelJob, params);
        return "Hotel job triggered!";
    }

    @GetMapping("/run-pricing-job/{jobId}")
    public String pricingJob(@PathVariable Long jobId) throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("jobId", jobId)
                .toJobParameters();
        jobLauncher.run(pricingJob, params);
        return "Pricing job triggered!";
    }

    @GetMapping("/run-voyage-job")
    public String voyageJob() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(voyageJob, params);
        return "Voyage job triggered!";
    }

    /*
     * @GetMapping("/run-transfer-job")
     * public String transferJob() throws Exception {
     * Long t1 = System.currentTimeMillis();
     * JobParameters params = new JobParametersBuilder()
     * .addLong("time", System.currentTimeMillis())
     * .toJobParameters();
     * jobLauncher.run(transferJob, params);
     * Long t2 = System.currentTimeMillis();
     * System.out.println("Duration--" + (t2-t1));
     * return "Transfer job triggered!";
     * }
     */

    @GetMapping("/run-transfer-job/{jobId}")
    public String transferJob(@PathVariable Long jobId) throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("jobId", jobId)
                .toJobParameters();
        jobLauncher.run(transferJob, params);
        return "Transfer job triggered!";
    }

    @GetMapping("/run-excursion-voyage-job/{jobId}")
    public String excursionVoyageJob(@PathVariable Long jobId) throws Exception {
         jobExecutionService.runExcursionVoyage(jobId);
        return "Excursion Voyage job triggered!";
    }

    @GetMapping("/test")
    public String testMethod() {
        auditService.logAudit(999999l, "TEST_PROCESS_NAME", LocalDateTime.now(), LocalDateTime.now(),
                LocalDateTime.now(), "Test Description");
        return new String("Region");
    }


    @GetMapping("/run-suite-job/{jobId}")
    public String suiteJob(@PathVariable Long jobId) throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("jobId", jobId)
                .toJobParameters();
        jobLauncher.run(suiteJob, params);
        return "Suite job triggered!";
    }

}

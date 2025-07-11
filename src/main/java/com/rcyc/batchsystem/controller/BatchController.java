package com.rcyc.batchsystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rcyc.batchsystem.service.ElasticService;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api")
public class BatchController {

    @Autowired
    private ElasticService elasticService;

     @Autowired
    private JobLauncher jobLauncher;

   
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

    @GetMapping("/region")
    public String getMethodName() {
        elasticService.getRegionData();
        return new String("Region");
    }

     @GetMapping("/run-region-job")
    public String runRegionJob() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(regionJob, params);
        return "Region job triggered!";
    }

    @GetMapping("/run-port-job")
    public String runPortJob() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(portJob, params);
        return "Port job triggered!";
    }

    @GetMapping("/run-itinerary-job")
    public String itineraryJob() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(itineraryJob, params);
        return "Itinerary job triggered!";
    }

    @GetMapping("/run-hotel-job")
    public String hotelJob() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(hotelJob, params);
        return "Hotel job triggered!";
    }

    @GetMapping("/run-pricing-job")
    public String pricingJob() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(pricingJob, params);
        return "Pricing job triggered!";
    }

    @GetMapping("/run-voyage-job")
    public String voyageJob() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(voygaeJob, params);
        return "Voyage job triggered!";
    }

}

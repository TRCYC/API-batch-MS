package com.rcyc.batchsystem.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

@Service
public class DelayedJobScheduler {
    private final JobLauncher jobLauncher;

    public DelayedJobScheduler(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    public void scheduleJob(Job job, JobParameters parameters, int delayMinutes) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        Runnable task = () -> {
            try {
                jobLauncher.run(job, parameters);
                System.out.println(" Job executed successfully after " + delayMinutes + " minute(s)");
            } catch (Exception e) {
                System.err.println(" Error executing job:");
                e.printStackTrace();
            } finally {
                scheduler.shutdown();
            }
        };
        scheduler.schedule(task, delayMinutes, TimeUnit.MINUTES);
    }
}

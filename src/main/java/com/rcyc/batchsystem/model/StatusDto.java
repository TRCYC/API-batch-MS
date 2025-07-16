package com.rcyc.batchsystem.model;

public class StatusDto {
    private Long jobId;
    private String process;
    private String status;
    private String currentCount;

    public StatusDto() {}

    public StatusDto(Long jobId, String process, String status, String currentCount) {
        this.jobId = jobId;
        this.process = process;
        this.status = status;
        this.currentCount = currentCount;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(String currentCount) {
        this.currentCount = currentCount;
    }

    @Override
    public String toString() {
        return "StatusDto{" +
                "jobId=" + jobId +
                ", process='" + process + '\'' +
                ", status='" + status + '\'' +
                ", currentCount='" + currentCount + '\'' +
                '}';
    }
} 
package com.rcyc.batchsystem.util;

public enum JobStatus {
    PENDING(0), RUNNING(1), COMPLETED(2), FAILED(3);
 
    private final int code;

    JobStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static JobStatus fromCode(int code) {
        for (JobStatus status : JobStatus.values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid JobStatus code: " + code);
    }
}

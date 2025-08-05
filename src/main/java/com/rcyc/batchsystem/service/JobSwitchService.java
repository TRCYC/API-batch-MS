package com.rcyc.batchsystem.service;

import java.util.List;

public interface JobSwitchService {
    public void doJobSwitch(Long jobId);

    public boolean validateCMS();

    public boolean checkDataVariation();

    public void filterData();

    public boolean reindexFromTempToLive();

    public void deleteFromQueue(Long jobId);

}

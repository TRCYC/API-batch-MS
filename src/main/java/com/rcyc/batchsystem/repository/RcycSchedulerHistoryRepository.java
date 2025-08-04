package com.rcyc.batchsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rcyc.batchsystem.entity.RcycSchedulerHistory;

public interface RcycSchedulerHistoryRepository extends JpaRepository<RcycSchedulerHistory, Integer> {
    
    RcycSchedulerHistory findByJobId(Integer jobId);
    
    void deleteByJobId(Integer jobId);
}


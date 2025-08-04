package com.rcyc.batchsystem.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rcyc.batchsystem.entity.RcycSchedulerQueue;

@Repository
public interface RcycSchedulerQueueRepository extends JpaRepository<RcycSchedulerQueue, Integer> {

    @Query("SELECT r FROM RcycSchedulerQueue r WHERE r.schedulerId = :schedulerId ORDER BY r.createdTime ASC")
    RcycSchedulerQueue findOldestBySchedulerId(@Param("schedulerId") Integer schedulerId);
}

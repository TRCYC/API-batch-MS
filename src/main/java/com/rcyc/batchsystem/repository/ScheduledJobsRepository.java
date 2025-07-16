package com.rcyc.batchsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rcyc.batchsystem.entity.ScheduledJob;

@Repository
public interface ScheduledJobsRepository extends JpaRepository<ScheduledJob, Long> {

    ScheduledJob findByExternalJobId(Long jobId);

    List<ScheduledJob> findByJobStatusAndSchedulerId(Integer jobStatus, Long schedulerId);

    @Modifying
    @Query("UPDATE ScheduledJob s SET s.jobStatus = :jobStatus WHERE s.id = :id")
    void updateJobStatusById(@Param("jobStatus") Integer jobStatus, @Param("id") Long id);

    @Query(nativeQuery = true,value = "select scheduler_name from rcyc_scheduler where id_scheduler=?1")
    Optional<String> findSchedulerNameById(Integer schedulerId);
}

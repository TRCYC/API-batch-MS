package com.rcyc.batchsystem.repository;

import com.rcyc.batchsystem.entity.FeedDateRangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedDateRangeRepository extends JpaRepository<FeedDateRangeEntity, Integer> {
    // Add custom query methods if needed
    java.util.List<FeedDateRangeEntity> findByType(String type);

    @Query("SELECT f FROM FeedDateRangeEntity f WHERE f.type = :type")
    java.util.List<FeedDateRangeEntity> findByTypeCustom(@Param("type") String type);
} 
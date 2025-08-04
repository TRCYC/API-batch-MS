package com.rcyc.batchsystem.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.rcyc.batchsystem.entity.RcycDataVariationConfig;

public interface RcycDataVariationConfigRepository extends CrudRepository<RcycDataVariationConfig,Integer> {
       List<RcycDataVariationConfig> findByJobTypeAndDisableFalse(String jobType);
}

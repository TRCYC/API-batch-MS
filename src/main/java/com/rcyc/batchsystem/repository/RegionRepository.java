package com.rcyc.batchsystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rcyc.batchsystem.entity.RegionEntity;

@Repository
public interface RegionRepository extends CrudRepository<RegionEntity,Integer>{

}

package com.rcyc.batchsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rcyc.batchsystem.entity.RcycCmsCheck;

@Repository
public interface RcycCmsCheckRepository extends CrudRepository<RcycCmsCheck,Integer> {

     List<RcycCmsCheck> findByJobTypeAndDisableAndIsTaxonomy(String jobType, int disable, int isTaxonomy);


}

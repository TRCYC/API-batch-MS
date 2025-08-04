package com.rcyc.batchsystem.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.rcyc.batchsystem.entity.RcycDataVariationAction;

public interface RcycDataVariationActionRepository extends CrudRepository<RcycDataVariationAction,Integer>{

     List<RcycDataVariationAction> findByIdVariation(Integer idVariation);
     
}

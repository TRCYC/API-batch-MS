package com.rcyc.batchsystem.model.job;

import java.util.List;

import com.rcyc.batchsystem.model.elastic.Region;

public class RegionPayLoad {
    private Region regionRequest;
    private Object regionReader;
    private List<Region> regionResponse;
    
    public RegionPayLoad() {
    }

    public RegionPayLoad(Region regionRequest, Object regionReader) {
        this.regionRequest = regionRequest;
        this.regionReader = regionReader;
    }

    public Region getRegionRequest() {
        return regionRequest;
    }

    public void setRegionRequest(Region regionRequest) {
        this.regionRequest = regionRequest;
    }

    public Object getRegionReader() {
        return regionReader;
    }

    public void setRegionReader(Object regionReader) {
        this.regionReader = regionReader;
    }

    public List<Region> getRegionResponse() {
        return regionResponse;
    }

    public void setRegionResponse(List<Region> regionResponse) {
        this.regionResponse = regionResponse;
    }
 
 

}

package com.rcyc.batchsystem.model.job;

public class RegionResponse {
    private String name;
    private boolean active;

    public RegionResponse() {
    }

    public RegionResponse(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}

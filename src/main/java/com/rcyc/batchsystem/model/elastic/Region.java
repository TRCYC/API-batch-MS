package com.rcyc.batchsystem.model.elastic;

import java.io.Serializable;

public class Region implements Serializable {
    private String portCode;
    private String createdDate;
    private String web_region_code;
    private String region_url;
    private String region_code;
    private String region_name;

    public Region() {
    }
     
    public Region(String portCode, String createdDate, String web_region_code, String region_url, String region_code,
            String region_name) {
        this.portCode = portCode;
        this.createdDate = createdDate;
        this.web_region_code = web_region_code;
        this.region_url = region_url;
        this.region_code = region_code;
        this.region_name = region_name;
    }

    public String getPortCode() {
        return portCode;
    }

    public void setPortCode(String portCode) {
        this.portCode = portCode;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getWeb_region_code() {
        return web_region_code;
    }

    public void setWeb_region_code(String web_region_code) {
        this.web_region_code = web_region_code;
    }

    public String getRegion_url() {
        return region_url;
    }

    public void setRegion_url(String region_url) {
        this.region_url = region_url;
    }

    public String getRegion_code() {
        return region_code;
    }

    public void setRegion_code(String region_code) {
        this.region_code = region_code;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    @Override
    public String toString() {
        return "Region [portCode=" + portCode + ", createdDate=" + createdDate + ", web_region_code=" + web_region_code
                + ", region_url=" + region_url + ", region_code=" + region_code + ", region_name=" + region_name + "]";
    }

}

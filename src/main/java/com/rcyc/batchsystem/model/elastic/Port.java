package com.rcyc.batchsystem.model.elastic;

import java.util.List;

public class Port {
    private String timeZone;
    private Long portId;
    private String portLatitude;
    private String portCode;
    private String portName;
    private List<PortLogstashFinish> port_logstash_finish;
    private String portRegion;
    private String createdDate;
    private String countryCode;
    private String enabled;
    private String portLongitude;
    private String comments;

    public Port() {
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Long getPortId() {
        return portId;
    }

    public void setPortId(Long portId) {
        this.portId = portId;
    }

    public String getPortLatitude() {
        return portLatitude;
    }

    public void setPortLatitude(String portLatitude) {
        this.portLatitude = portLatitude;
    }

    public String getPortCode() {
        return portCode;
    }

    public void setPortCode(String portCode) {
        this.portCode = portCode;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public List<PortLogstashFinish> getPort_logstash_finish() {
        return port_logstash_finish;
    }

    public void setPort_logstash_finish(List<PortLogstashFinish> port_logstash_finish) {
        this.port_logstash_finish = port_logstash_finish;
    }

    public String getPortRegion() {
        return portRegion;
    }

    public void setPortRegion(String portRegion) {
        this.portRegion = portRegion;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getPortLongitude() {
        return portLongitude;
    }

    public void setPortLongitude(String portLongitude) {
        this.portLongitude = portLongitude;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    // Nested class for port_logstash_finish
    public static class PortLogstashFinish {
        private int audit_id;

        public int getAudit_id() {
            return audit_id;
        }

        public void setAudit_id(int audit_id) {
            this.audit_id = audit_id;
        }
    }
}

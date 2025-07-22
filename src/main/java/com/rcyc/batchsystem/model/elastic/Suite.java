package com.rcyc.batchsystem.model.elastic;

import java.util.List;

public class Suite {
    private String portCode;
    private String voyageId;
    private String createdDate;
    private String voyageCode;
    private List<SuiteProcessFinished> suite_process_finished;
    private List<VoyageSuite> suites = null;

    public String getPortCode() {
        return portCode;
    }

    public void setPortCode(String portCode) {
        this.portCode = portCode;
    }

    public String getVoyageId() {
        return voyageId;
    }

    public void setVoyageId(String voyageId) {
        this.voyageId = voyageId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getVoyageCode() {
        return voyageCode;
    }

    public void setVoyageCode(String voyageCode) {
        this.voyageCode = voyageCode;
    }

    public List<SuiteProcessFinished> getSuite_process_finished() {
        return suite_process_finished;
    }

    public void setSuite_process_finished(List<SuiteProcessFinished> suite_process_finished) {
        this.suite_process_finished = suite_process_finished;
    }

    public List<VoyageSuite> getSuites() {
        return suites;
    }

    public void setSuites(List<VoyageSuite> suites) {
        this.suites = suites;
    }

}

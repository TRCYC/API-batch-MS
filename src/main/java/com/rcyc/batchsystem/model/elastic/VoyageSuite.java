package com.rcyc.batchsystem.model.elastic;

public class VoyageSuite {
    private String suiteCategoryCode;
    private String suiteCategoryType;
    private String suiteCategoryName;
    private Integer suiteId;

    public String getSuiteCategoryCode() {
        return suiteCategoryCode;
    }

    public void setSuiteCategoryCode(String suiteCategoryCode) {
        this.suiteCategoryCode = suiteCategoryCode;
    }

    public String getSuiteCategoryType() {
        return suiteCategoryType;
    }

    public void setSuiteCategoryType(String suiteCategoryType) {
        this.suiteCategoryType = suiteCategoryType;
    }

    public String getSuiteCategoryName() {
        return suiteCategoryName;
    }

    public void setSuiteCategoryName(String suiteCategoryName) {
        this.suiteCategoryName = suiteCategoryName;
    }

    public Integer getSuiteId() {
        return suiteId;
    }

    public void setSuiteId(Integer suiteId) {
        this.suiteId = suiteId;
    }
}

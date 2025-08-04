package com.rcyc.batchsystem.entity;

import javax.persistence.*;

@Entity
@Table(name = "rcyc_data_variation_config")
public class RcycDataVariationConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_variation")
    private Integer idVariation;

    @Column(name = "job_type")
    private String jobType;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "statement")
    private String statement;

    @Column(name = "disable")
    private boolean disable;

    public RcycDataVariationConfig() {
    }

    // Getters and Setters

    public Integer getIdVariation() {
        return idVariation;
    }

    public void setIdVariation(Integer idVariation) {
        this.idVariation = idVariation;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    @Override
    public String toString() {
        return "RcycDataVariationConfig [idVariation=" + idVariation + ", jobType=" + jobType + ", fieldName="
                + fieldName + ", statement=" + statement + ", disable=" + disable + "]";
    }

    
}


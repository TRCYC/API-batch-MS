package com.rcyc.batchsystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="rcyc_cms_check")
public class RcycCmsCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	@Column(name="job_type")
	private String jobType;
	@Column(name="content_name")
	private String contentName;
	@Column(name="content_description")
	private String contentDescription;
	@Column(name="content_url")
	private String contentUrl;
	@Column(name="is_required")
	private int isRequired;
    @Column(name="is_taxonomy")
    private int isTaxonomy;
    @Column(name="disable")
    private int disable;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getJobType() {
        return jobType;
    }
    public void setJobType(String jobType) {
        this.jobType = jobType;
    }
    public String getContentName() {
        return contentName;
    }
    public void setContentName(String contentName) {
        this.contentName = contentName;
    }
    public String getContentDescription() {
        return contentDescription;
    }
    public void setContentDescription(String contentDescription) {
        this.contentDescription = contentDescription;
    }
    public String getContentUrl() {
        return contentUrl;
    }
    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }
    public int getIsRequired() {
        return isRequired;
    }
    public void setIsRequired(int isRequired) {
        this.isRequired = isRequired;
    }
    public int getIsTaxonomy() {
        return isTaxonomy;
    }
    public void setIsTaxonomy(int isTaxonomy) {
        this.isTaxonomy = isTaxonomy;
    }
    public int getDisable() {
        return disable;
    }
    public void setDisable(int disable) {
        this.disable = disable;
    }



}

package com.rcyc.batchsystem.model.resco;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List; 

@XmlRootElement(name = "ResListCategory")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResListCategory {
    @XmlElement(name = "Result")
    private Result result;
    @XmlElementWrapper(name = "CategoryList")
    @XmlElement(name = "Category")
    private List<Category> categoryList;
    private String cruiseCode;

    public Result getResult() { return result; }
    public void setResult(Result result) { this.result = result; }
    public List<Category> getCategoryList() { return categoryList; }
    public void setCategoryList(List<Category> categoryList) { this.categoryList = categoryList; }
    public String getCruiseCode() {
        return cruiseCode;
    }
    public void setCruiseCode(String cruiseCode) {
        this.cruiseCode = cruiseCode;
    }
} 
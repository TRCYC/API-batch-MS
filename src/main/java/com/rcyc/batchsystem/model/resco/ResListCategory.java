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

    public Result getResult() { return result; }
    public void setResult(Result result) { this.result = result; }
    public List<Category> getCategoryList() { return categoryList; }
    public void setCategoryList(List<Category> categoryList) { this.categoryList = categoryList; }

    public static class Category {
        @XmlElement(name = "CategoryId")
        private String categoryId;
        @XmlElement(name = "Code")
        private String code;
        @XmlElement(name = "Name")
        private String name;
        @XmlElement(name = "Sort")
        private String sort;
        @XmlElement(name = "Type")
        private String type;
        @XmlElement(name = "RegularCapacity")
        private int regularCapacity;
        @XmlElement(name = "AvailUnits")
        private int availUnits;
        // Add RateList and other fields as needed
        public String getCategoryId() { return categoryId; }
        public void setCategoryId(String categoryId) { this.categoryId = categoryId; }
        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getSort() { return sort; }
        public void setSort(String sort) { this.sort = sort; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public int getRegularCapacity() { return regularCapacity; }
        public void setRegularCapacity(int regularCapacity) { this.regularCapacity = regularCapacity; }
        public int getAvailUnits() { return availUnits; }
        public void setAvailUnits(int availUnits) { this.availUnits = availUnits; }
    }
} 
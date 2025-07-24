package com.rcyc.batchsystem.model.resco;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name = "FlexItem")
@XmlAccessorType(XmlAccessType.FIELD)
public class FlexItem {

    @XmlElement(name = "ItemId")
    private Integer itemId;
    @XmlElement(name = "XmlTag")
    private String xmlTag;
    @XmlElement(name = "Label")
    private String label;
     @XmlElement(name = "Sort")
    private String sort;
    @XmlElement(name = "Type")
    private String type;
    @XmlElement(name = "Required")
    private int required;
    @XmlElement(name = "Locked")
    private String locked;

    @XmlElementWrapper(name = "DefaultValueList")
	@XmlElement(name = "DefaultValue")
	private List<DefaultValue> defaultValueList;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getXmlTag() {
        return xmlTag;
    }

    public void setXmlTag(String xmlTag) {
        this.xmlTag = xmlTag;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRequired() {
        return required;
    }

    public void setRequired(int required) {
        this.required = required;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public List<DefaultValue> getDefaultValueList() {
        return defaultValueList;
    }

    public void setDefaultValueList(List<DefaultValue> defaultValueList) {
        this.defaultValueList = defaultValueList;
    } 


    
}

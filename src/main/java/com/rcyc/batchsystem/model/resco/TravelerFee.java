package com.rcyc.batchsystem.model.resco;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;
import com.rcyc.batchsystem.model.resco.Surcharge;

@XmlAccessorType(XmlAccessType.FIELD)
public class TravelerFee {
    @XmlElement(name = "Id")
    private String id;
    @XmlElement(name = "AgeGroup")
    private String ageGroup;
    @XmlElement(name = "Amount")
    private String amount;
    @XmlElement(name = "BaseAmount")
    private String baseAmount;
    @XmlElementWrapper(name = "SurchargeList")
    @XmlElement(name = "Surcharge")
    private List<Surcharge> surchargeList;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getAgeGroup() { return ageGroup; }
    public void setAgeGroup(String ageGroup) { this.ageGroup = ageGroup; }
    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }
    public String getBaseAmount() { return baseAmount; }
    public void setBaseAmount(String baseAmount) { this.baseAmount = baseAmount; }
    public List<Surcharge> getSurchargeList() { return surchargeList; }
    public void setSurchargeList(List<Surcharge> surchargeList) { this.surchargeList = surchargeList; }
} 
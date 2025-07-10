package com.rcyc.batchsystem.model.resco;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlAccessorType(XmlAccessType.FIELD)
public class Rate {
    @XmlElement(name = "RateId")
    private String rateId;
    @XmlElement(name = "Code")
    private String code;
    @XmlElement(name = "Name")
    private String name;
    @XmlElement(name = "InfoPrice")
    private String infoPrice;
    @XmlElement(name = "Amount")
    private String amount;
    @XmlElement(name = "BaseAmount")
    private String baseAmount;
    @XmlElementWrapper(name = "TravelerFeeList")
    @XmlElement(name = "TravelerFee")
    private java.util.List<TravelerFee> travelerFeeList;
    @XmlElement(name = "PriceCurrency")
    private String priceCurrency;
    @XmlElement(name = "Price")
    private String price;

    public String getRateId() { return rateId; }
    public void setRateId(String rateId) { this.rateId = rateId; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getInfoPrice() { return infoPrice; }
    public void setInfoPrice(String infoPrice) { this.infoPrice = infoPrice; }
    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }
    public String getBaseAmount() { return baseAmount; }
    public void setBaseAmount(String baseAmount) { this.baseAmount = baseAmount; }
    public java.util.List<TravelerFee> getTravelerFeeList() { return travelerFeeList; }
    public void setTravelerFeeList(java.util.List<TravelerFee> travelerFeeList) { this.travelerFeeList = travelerFeeList; }
    public String getPriceCurrency() { return priceCurrency; }
    public void setPriceCurrency(String priceCurrency) { this.priceCurrency = priceCurrency; }
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
} 
package com.rcyc.batchsystem.model.resco;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.rcyc.batchsystem.model.resco.Agency;
import com.rcyc.batchsystem.model.resco.Category;
import com.rcyc.batchsystem.model.resco.Rate;

@XmlRootElement(name = "ReqListCategory")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReqListCategory {
    @XmlElement(name = "User")
    private User user;
    @XmlElement(name = "Agency")
    private Agency agency;
    @XmlElement(name = "Category")
    private Category category;
    @XmlElement(name = "Rate")
    private Rate rate;

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Agency getAgency() { return agency; }
    public void setAgency(Agency agency) { this.agency = agency; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public Rate getRate() { return rate; }
    public void setRate(Rate rate) { this.rate = rate; }
} 
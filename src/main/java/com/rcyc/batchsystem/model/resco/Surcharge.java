package com.rcyc.batchsystem.model.resco;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Surcharge {
    @XmlElement(name = "Id")
    private String id;
    @XmlElement(name = "Code")
    private String code;
    @XmlElement(name = "Name")
    private String name;
    @XmlElement(name = "Type")
    private String type;
    @XmlElement(name = "Scope")
    private String scope;
    @XmlElement(name = "Amount")
    private String amount;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getScope() { return scope; }
    public void setScope(String scope) { this.scope = scope; }
    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }
} 
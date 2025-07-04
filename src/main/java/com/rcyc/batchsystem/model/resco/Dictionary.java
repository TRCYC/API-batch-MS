package com.rcyc.batchsystem.model.resco;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Dictionary") 
@XmlAccessorType(XmlAccessType.FIELD)
public class Dictionary {
    @XmlElement(name = "Group")
    private String group;
    @XmlElement(name = "Code")
    private String code;
    @XmlElement(name = "Name")
    private String name;
    @XmlElement(name = "Enabled")
    private int enabled;
    @XmlElement(name = "Disabled")
    private int disabled;

    public Dictionary() {
    }

    public Dictionary(String group, String code, String name, int enabled) {
        this.group = group;
        this.code = code;
        this.name = name;
        this.enabled = enabled;
    }

    public Dictionary(String group, String code, String name, int enabled, int disabled) {
        this.group = group;
        this.code = code;
        this.name = name;
        this.enabled = enabled;
        this.disabled = disabled;
    }

    public Dictionary(String group, int disabled) {
        this.group = group;
        this.disabled = disabled;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public int getDisabled() {
        return disabled;
    }

    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }

    @Override
    public String toString() {
        return "Dictionary [group=" + group + ", code=" + code + ", name=" + name + ", enabled=" + enabled
                + ", disabled=" + disabled + "]";
    }

}

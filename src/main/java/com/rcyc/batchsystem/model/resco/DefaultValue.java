package com.rcyc.batchsystem.model.resco;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DefaultValue")
@XmlAccessorType(XmlAccessType.FIELD)
public class DefaultValue {
    @XmlElement(name = "No")
    private int no;

    @Override
    public String toString() {
        return "DefaultValue [no=" + no + ", value=" + value + "]";
    }

    @XmlElement(name = "Value")
    private String value;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}

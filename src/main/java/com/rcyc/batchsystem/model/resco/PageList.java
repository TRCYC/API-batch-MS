package com.rcyc.batchsystem.model.resco;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PageList")
@XmlAccessorType(XmlAccessType.FIELD)
public class PageList {

    @XmlElement(name = "page")
    private List<Page> page;

    public List<Page> getPage() {
        return page;
    }

    public void setPage(List<Page> page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "PageList [page=" + page + "]";
    }

}

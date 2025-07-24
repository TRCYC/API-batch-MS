package com.rcyc.batchsystem.model.resco;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Page")
@XmlAccessorType(XmlAccessType.FIELD)
public class Page {
    @XmlElement(name = "PageId")
    private Long pageId;
    @XmlElement(name = "Language")
    private String language;
    @XmlElement(name = "Text")
    private String text;

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Page [pageId=" + pageId + ", language=" + language + ", text=" + text + "]";
    }

}

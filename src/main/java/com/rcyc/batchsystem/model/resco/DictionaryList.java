package com.rcyc.batchsystem.model.resco;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DictionaryList") 
@XmlAccessorType(XmlAccessType.FIELD)
public class DictionaryList {
    @XmlElement(name = "Dictionary")
    private List<Dictionary> dictionary;

    public DictionaryList() {
    }

    public DictionaryList(List<Dictionary> dictionary) {
        this.dictionary = dictionary;
    }

    public List<Dictionary> getDictionary() {
        return dictionary;
    }

    public void setDictionary(List<Dictionary> dictionary) {
        this.dictionary = dictionary;
    }

}

package com.rcyc.batchsystem.model.resco;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ResListDictionary") 
@XmlAccessorType(XmlAccessType.FIELD)
public class ResListDictionary {
    @XmlElement(name = "DictionaryList")
    private DictionaryList dictionaryList;
    @XmlElement(name = "Result")
    private Result result;

    public ResListDictionary() {
    }

    public ResListDictionary(DictionaryList dictionaryList) {
        this.dictionaryList = dictionaryList;
    }

    public ResListDictionary(DictionaryList dictionaryList, Result result) {
        this.dictionaryList = dictionaryList;
        this.result = result;
    }

    public DictionaryList getDictionaryList() {
        return dictionaryList;
    }

    public void setDictionaryList(DictionaryList dictionaryList) {
        this.dictionaryList = dictionaryList;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResListDictionary [dictionaryList=" + dictionaryList + ", result=" + result + "]";
    }

}

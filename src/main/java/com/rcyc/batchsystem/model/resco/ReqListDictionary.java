package com.rcyc.batchsystem.model.resco;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ReqListDictionary") 
@XmlAccessorType(XmlAccessType.FIELD)
public class ReqListDictionary {
    @XmlElement(name = "User")
    private User user;
     @XmlElement(name="Dictionary")
    private Dictionary dictionary;

    public ReqListDictionary() {
    }

    public ReqListDictionary(User user, Dictionary dictionary) {
        this.user = user;
        this.dictionary = dictionary;
    }

    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

   
    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

}

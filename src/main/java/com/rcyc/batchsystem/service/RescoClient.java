package com.rcyc.batchsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rcyc.batchsystem.model.resco.Dictionary;
import com.rcyc.batchsystem.model.resco.Location;
import com.rcyc.batchsystem.model.resco.ReqListDictionary;
import com.rcyc.batchsystem.model.resco.ReqListLocation;
import com.rcyc.batchsystem.model.resco.ResListDictionary;
import com.rcyc.batchsystem.model.resco.ResListLocation;
import com.rcyc.batchsystem.model.resco.User;

@Service
public class RescoClient {

    @Autowired
    private RestTemplate restTemplate;

    public ResListDictionary getAllRegions(){
        User user =new User("webapiprod1","theGr8tw1de0pen#305");
        Dictionary dictionary = new Dictionary("RGN", 0);
        ReqListDictionary req = new ReqListDictionary(user, dictionary);
        ResListDictionary response =  restTemplate.postForObject("https://stgwebapi.ritz-carltonyachtcollection.com/rescoweb/ResWebConvert/InterfaceResco.aspx", req, ResListDictionary.class);
       System.out.println(response.toString());
        return response;
    }

    public ResListLocation getAllPorts(String type){
        ReqListLocation reqListLocation = new ReqListLocation(new User("webapiprod1","theGr8tw1de0pen#305"),new Location(0,type));
        ResListLocation response =  restTemplate.postForObject("https://stgwebapi.ritz-carltonyachtcollection.com/rescoweb/ResWebConvert/InterfaceResco.aspx", reqListLocation, ResListLocation.class);
         System.out.println(response.toString());
        return response;
    }



}

package com.rcyc.batchsystem.model.job;

import java.util.List;

public class DefaultPayLoad<T, R, S> {

    public T request;
    public R reader;
    public List<S> response;

    public DefaultPayLoad() {
    }

    public DefaultPayLoad(T request, R reader, List<S> response) {
        this.request = request;
        this.reader = reader;
        this.response = response;
    }

    public List<S> getResponse() {
        return response;
    }

    public void setResponse(List<S> response) {
        this.response = response;
    }

    public R getReader() {
        return reader;
    }

    public void setReader(R reader) {
        this.reader = reader;
    }

    public T getRequest() {
        return request;
    }

    public void setRequest(T request) {
        this.request = request;
    }
    
}

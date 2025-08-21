package com.rcyc.batchsystem.service;

import java.net.http.HttpClient;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rcyc.batchsystem.model.StatusDto;
import com.rcyc.batchsystem.util.Constants;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.logging.Logger;

@Component
public class ExternalApiClient {
    private Logger logger = Logger.getLogger(ExternalApiClient.class.getName());
    private final HttpClient httpClient;

    public ExternalApiClient() {
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public void callBack(StatusDto responseDto) {
        try {
            logger.info(Constants.SCHEDULER_API);
            String response = callExternalApiPost(Constants.SCHEDULER_API+"/updateStatus", responseDto);
            System.out.println("Callback response: " + response);
        } catch (Exception e) {
            System.err.println("Callback failed: " + e.getMessage());
        }
    }

    public String callExternalApiGet(String baseUrl, Long jobId) throws IOException, InterruptedException {
        String url = String.format("%s?jobId=%d", baseUrl, jobId);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
 
    public String callExternalApiPost(String url, StatusDto responseDto) throws IOException, InterruptedException {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        logger.info(url);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(responseDto);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .timeout(Duration.ofSeconds(10))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

}

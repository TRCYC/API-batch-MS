package com.rcyc.batchsystem.configuration;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient; 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rcyc.batchsystem.util.Constants;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;

@Configuration
public class ElasticsearchConfig {
    @Bean
    public ElasticsearchClient elasticsearchClient() {
        RestClient restClient = RestClient.builder(
                new HttpHost(Constants.ELASTIC_BASE_IP, 9200)).build();

        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());

        return new ElasticsearchClient(transport);
    }
}

package com.rcyc.batchsystem.configuration;
 
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
   @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        HttpMessageConverter<Object> xmlConverter = new Jaxb2RootElementHttpMessageConverter();
        restTemplate.setMessageConverters(Collections.singletonList(xmlConverter));
        return restTemplate;
    }
}

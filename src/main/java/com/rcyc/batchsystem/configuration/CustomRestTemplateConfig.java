package com.rcyc.batchsystem.configuration;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CustomRestTemplateConfig {

	@Bean
    public RestTemplate restCustomTemplate(Jaxb2Marshaller marshaller) {
        MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();
        xmlConverter.setMarshaller(marshaller);
        xmlConverter.setUnmarshaller(marshaller);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(Collections.singletonList(xmlConverter));
        return restTemplate;
    }
}

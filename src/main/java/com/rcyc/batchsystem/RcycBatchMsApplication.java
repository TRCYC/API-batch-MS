package com.rcyc.batchsystem;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync; 

@SpringBootApplication
@EnableBatchProcessing
@EnableAsync
public class RcycBatchMsApplication {

	public static void main(String[] args) {

		//System.setProperty("javax.xml.bind.context.factory", "org.glassfish.jaxb.runtime.v2.ContextFactory");

		SpringApplication.run(RcycBatchMsApplication.class, args);
	}

}

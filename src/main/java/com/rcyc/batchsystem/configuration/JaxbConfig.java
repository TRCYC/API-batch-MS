package com.rcyc.batchsystem.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class JaxbConfig {

	@Bean
	public Jaxb2Marshaller jaxb2Marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(com.rcyc.batchsystem.model.resco.ReqListItem.class,
				com.rcyc.batchsystem.model.resco.ResListItem.class, com.rcyc.batchsystem.model.resco.Item.class,
				com.rcyc.batchsystem.model.resco.User.class, com.rcyc.batchsystem.model.resco.Agency.class,
				com.rcyc.batchsystem.model.resco.Event.class, com.rcyc.batchsystem.model.resco.Facility.class,
				com.rcyc.batchsystem.model.resco.Availability.class);
		return marshaller;
	}
}

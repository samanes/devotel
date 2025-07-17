package com.devotel.profileservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

@Configuration
public class SoapClientConfig {

    private static final Logger log = LoggerFactory.getLogger(SoapClientConfig.class);

    @Value("${soap.client.uri}")
    private String userServiceUri;

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.devotel.userservice.soap.gen");
        return marshaller;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate(Jaxb2Marshaller marshaller) {
        WebServiceTemplate ws = new WebServiceTemplate();
        ws.setMarshaller(marshaller);
        ws.setUnmarshaller(marshaller);
        ws.setDefaultUri(userServiceUri);
        log.info(">>> WebServiceTemplate defaultUri set to {}", userServiceUri);
        return ws;
    }
}
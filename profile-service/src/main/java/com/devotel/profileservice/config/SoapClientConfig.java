package com.devotel.profileservice.config;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SoapClientConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller m = new Jaxb2Marshaller();
        m.setContextPath("com.devotel.userservice.soap.gen");
        return m;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate(Jaxb2Marshaller marshaller) {
        WebServiceTemplate wst = new WebServiceTemplate();
        wst.setMarshaller(marshaller);
        wst.setUnmarshaller(marshaller);
        wst.setDefaultUri("http://localhost:8081/ws");
        return wst;
    }
}

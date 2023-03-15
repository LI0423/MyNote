package com.video.oversea.payment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriTemplateHandler;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(@Autowired ClientHttpRequestFactory factory,
                                     @Qualifier("valuesOnlyDefaultUriBuilderFactory") UriTemplateHandler uriTemplateHandler){
        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.setUriTemplateHandler(uriTemplateHandler);
        return restTemplate;
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(15000);
        factory.setReadTimeout(5000);
        return factory;
    }

    @Bean("valuesOnlyDefaultUriBuilderFactory")
    public UriTemplateHandler valuesOnlyDefaultUriBuilderFactory() {
        DefaultUriBuilderFactory uriFactory = new DefaultUriBuilderFactory();
        uriFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
        return uriFactory;
    }
}


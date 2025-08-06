package com.sbarrasa.extractor.config;

import com.sbarrasa.extractor.service.llama.LlamaClient;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Configuration
public class LlamaConfig {

  @Bean
  public RestTemplate llamaRestTemplate(RestTemplateBuilder builder) {
    return builder
            .rootUri("http://localhost:11434/api")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
  }

  @Bean
  public LlamaClient llamaClient(RestTemplate llamaRestTemplate) {
    return new LlamaClient(llamaRestTemplate, "gemma3:4b");
  }
}
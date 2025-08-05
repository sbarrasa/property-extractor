package com.sbarrasa.extractor.config;

import com.sbarrasa.extractor.service.LlamaClient;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class LlamaConfig {

  @Bean
  public RestTemplate llamaRestTemplate(RestTemplateBuilder builder) {
    /*var requestFactory = new HttpComponentsClientHttpRequestFactory();
    requestFactory.setConnectTimeout(2000);
    requestFactory.setReadTimeout(2000);
*/
    return builder
 //           .requestFactory(() -> requestFactory)
            .rootUri("http://localhost:11434/api")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
  }

  @Bean
  public LlamaClient llamaClient(RestTemplate llamaRestTemplate) {
    return new LlamaClient(llamaRestTemplate, "gemma3:4b");
  }

  public LlamaClient llamaClient() {
    return llamaClient(llamaRestTemplate(new RestTemplateBuilder()));
  }
}
package com.sbarrasa.extractor.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Component
public class LlamaClient {

  private final RestTemplate llamaRestTemplate;
  private final String model;

  public LlamaClient(RestTemplate llamaRestTemplate, @Value("${llama.model}") String model) {
    this.llamaRestTemplate = llamaRestTemplate;
    this.model = model;
  }

  public String generate(String prompt) {
    var requestBody = Map.of(
            "model", model,
            "prompt", prompt,
            "stream", false
    );

    var request = new HttpEntity<>(requestBody);

    var response = llamaRestTemplate.postForEntity("/generate", request, String.class);
    var responseBody = response.getBody();

    if (!response.getStatusCode().is2xxSuccessful())
      throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error llamando a Llama: "
              + response.getStatusCode()
              + "\n" + responseBody);

    return responseBody;
  }
}

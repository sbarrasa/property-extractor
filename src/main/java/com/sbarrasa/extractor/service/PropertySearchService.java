package com.sbarrasa.extractor.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PropertySearchService {

    private final LlamaClient llamaClient;
    private final PromptService promptService;
    private final ObjectMapper objectMapper;

    public Map<String, Object> findProperties(Set<String> propertyKeys, String text) throws Exception {
        var prompt = promptService.buildPrompt(propertyKeys, text);
        var responseBody = llamaClient.generate(prompt);
        return objectMapper.readValue(responseBody, new TypeReference<>() {});
    }

}

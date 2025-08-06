package com.sbarrasa.extractor.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbarrasa.extractor.service.llama.LlamaClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        var response = llamaClient.generate(prompt);
        return buildProperties(response);
    }

    public Map<String, Object> buildProperties(String text) throws Exception {
       var cleaned = text.replaceAll("```json", "")
               .replaceAll("```", "")
               .trim();
       return objectMapper.readValue(cleaned, new TypeReference<>() {});
    }

}

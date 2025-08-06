package com.sbarrasa.extractor.service;

import com.sbarrasa.extractor.service.llama.LlamaClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LlamaClientTest {

  private RestTemplate rt;
  private LlamaClient client;
  private final String model = "gemma3:4b";

  @BeforeEach
  void setup() {
    rt = mock(RestTemplate.class);
    client = new LlamaClient(rt, model);
  }

  @Test
  void genOk() {
    var prompt = "prompt";
    var resp = "{\"key\":\"val\"}";

    var resEntity = new ResponseEntity<>(resp, HttpStatus.OK);
    when(rt.postForEntity(anyString(), any(HttpEntity.class), eq(String.class)))
            .thenReturn(resEntity);

    var result = client.generate(prompt);

    assertEquals(resp, result);

    var cap = ArgumentCaptor.forClass(HttpEntity.class);
    verify(rt).postForEntity(eq("/generate"), cap.capture(), eq(String.class));

    var body = cap.getValue().getBody();
    assertInstanceOf(Map.class, body);

    @SuppressWarnings("unchecked")
    var map = (Map<String, Object>) body;
    assertEquals(model, map.get("model"));
    assertEquals(prompt, map.get("prompt"));
    assertEquals(false, map.get("stream"));
  }

  @Test
  void genError() {
    var resEntity = new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
    when(rt.postForEntity(anyString(), any(HttpEntity.class), eq(String.class)))
            .thenReturn(resEntity);

    var ex = assertThrows(ResponseStatusException.class,
            () -> client.generate("prompt"));

    assertTrue(ex.getMessage().contains("Error llamando a Llama"));
  }
}


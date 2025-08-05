package com.sbarrasa.extractor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PropertySearchServiceTest {

  private LlamaClient client;
  private ObjectMapper mapper;
  private PromptService prompt;
  private PropertySearchService service;

  @BeforeEach
  void setup() {
    client = mock(LlamaClient.class);
    mapper = new ObjectMapper();
    prompt = new PromptService();
    service = new PropertySearchService(client, prompt, mapper);
  }

  @Test
  void findOk() throws Exception {
    var keys = Set.of("marca", "modelo");
    var text = "texto";

    var fakeJson = "{\"marca\":\"Drean\",\"modelo\":\"X100\"}";

    when(client.generate(anyString())).thenReturn(fakeJson);

    var res = service.findProperties(keys, text);

    assertEquals("Drean", res.get("marca"));
    assertEquals("X100", res.get("modelo"));

    verify(client).generate(anyString());
  }

  @Test
  void findFail() throws Exception {
    var keys = Set.of("marca");
    var text = "texto";

    when(client.generate(anyString())).thenReturn("no json");

    assertThrows(Exception.class, () -> service.findProperties(keys, text));
  }
}

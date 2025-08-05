package com.sbarrasa.extractor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbarrasa.extractor.config.LlamaConfig;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import static org.mockito.Mockito.*;

public class PropertySearchServiceTest {


  private final ObjectMapper mapper = new ObjectMapper();


  @Test
  void findOk() throws Exception {
    var promptService = new PromptService();
    var llamaClient = getLlamaClient();

    var keys = Set.of("marca", "modelo", "frigorías");
    var text = """
            Manual del Aire FríoX
            Marca: CoolTech
            Modelo: CT-5000
            Potencia: 5000 frigorías
            """;

    var service = new PropertySearchService(llamaClient, promptService, mapper);
    var properties = service.findProperties(keys, text);

    assertNotNull(properties);
    assertTrue(properties.containsKey("marca"));
    assertTrue(properties.containsKey("modelo"));
  }

  private LlamaClient getLlamaClient() {
    System.out.println("Intentando conectar con llama");

    LlamaClient llamaClient;

    try {
      llamaClient = new LlamaConfig().llamaClient();

      llamaClient.generate("ping");
      System.out.println("Test con llama real");
    } catch (Exception e) {
      llamaClient = mockLlamaClient();
      System.out.println("Test con llama mock");
    }

    return llamaClient;
  }


  private LlamaClient mockLlamaClient() {
    var mock = mock(LlamaClient.class);
    when(mock.generate(anyString()))
            .thenReturn("{\"marca\":\"MockMarca\",\"modelo\":\"MockModelo\"}");
    return mock;
  }
}

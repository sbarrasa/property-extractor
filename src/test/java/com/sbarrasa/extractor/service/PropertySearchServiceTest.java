package com.sbarrasa.extractor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbarrasa.extractor.service.llama.LlamaClient;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import static org.mockito.Mockito.*;

@Log
@SpringBootTest
public class PropertySearchServiceTest {

  private final ObjectMapper mapper = new ObjectMapper();
  @Autowired
  RestTemplate restTemplate;

  @Test
  void findOk() throws Exception {
    var promptService = new PromptService();
    var llamaClient = buildLlamaClient();

    log.info("Probar busqueda de properties");

    var keys = Set.of("marca", "modelo", "frigorías");
    var text = """
            Manual del Aire FríoX
            Marca: CoolTech
            Modelo: CT-5000
            Potencia: 5000 frigorías
            """;

    var service = new PropertySearchService(llamaClient, promptService, mapper);
    var properties = service.findProperties(keys, text);

    System.out.println(properties.toString());

    log.info("Busqueda de properties realizada");

    assertNotNull(properties);
    assertTrue(properties.containsKey("marca"));
    assertTrue(properties.containsKey("modelo"));
  }

  private LlamaClient buildLlamaClient() {
    log.info("Intentando conectar con llama");

    LlamaClient llamaClient;
    if (isllamaReady(restTemplate)) {
      llamaClient = new LlamaClient(restTemplate, "gemma3:4b");
      log.info("Conexión con llama establecida");
    } else {
      llamaClient = mockLlamaClient();
      log.info("Test con llama mock");
    }

    return llamaClient;
  }

  private LlamaClient mockLlamaClient () {
     var mock = mock(LlamaClient.class);
     when(mock.generate(anyString()))
        .thenReturn("{\"marca\":\"MockMarca\",\"modelo\":\"MockModelo\"}");
     return mock;
  }


  private boolean isllamaReady(RestTemplate restTemplate) {
    try {
      restTemplate.getForObject("/tags", String.class);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
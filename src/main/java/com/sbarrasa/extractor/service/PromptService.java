package com.sbarrasa.extractor.service;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PromptService {

  private static final String PROMPT_BASE = "Arma un JSON tipo mapa clave-valor buscando estos atributos o similares: %s dentro del siguiente texto:\n%s\nDevuelve solo el JSON válido.";

  public String buildPrompt(Set<String> keys, String texto) {
    String keysStr = String.join(", ", keys);
    return String.format(PROMPT_BASE, keysStr, texto);
  }
}

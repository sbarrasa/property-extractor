package com.sbarrasa.extractor.service.llama;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LlamaRequest {
  private String model;
  private String prompt;
  private boolean stream;

  public LlamaRequest(String model, String prompt) {
    this(model, prompt, false);
  }
}



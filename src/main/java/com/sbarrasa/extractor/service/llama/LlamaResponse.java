package com.sbarrasa.extractor.service.llama;

import lombok.Data;

@Data
public class LlamaResponse {
  private String model;
  private String created_at;
  private String response;
  private boolean done;
}

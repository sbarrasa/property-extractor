package com.sbarrasa.extractor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextExtractionResponse {
    private String message;
    private String extractedText;
}
package com.sbarrasa.extractor.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TextExtractionResponseTest {

    @Test
    void testDefaultConstructor() {
        TextExtractionResponse response = new TextExtractionResponse();
        assertNull(response.getMessage());
        assertNull(response.getExtractedText());
    }

    @Test
    void testParameterizedConstructor() {
        String message = "Test message";
        String extractedText = "Test extracted text";
        
        TextExtractionResponse response = new TextExtractionResponse(message, extractedText);
        
        assertEquals(message, response.getMessage());
        assertEquals(extractedText, response.getExtractedText());
    }

    @Test
    void testSettersAndGetters() {
        TextExtractionResponse response = new TextExtractionResponse();
        
        String message = "Updated message";
        String extractedText = "Updated extracted text";
        
        response.setMessage(message);
        response.setExtractedText(extractedText);
        
        assertEquals(message, response.getMessage());
        assertEquals(extractedText, response.getExtractedText());
    }
}
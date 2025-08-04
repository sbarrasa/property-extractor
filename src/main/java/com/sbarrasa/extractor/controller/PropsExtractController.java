package com.sbarrasa.extractor.controller;

import com.sbarrasa.extractor.dto.TextExtractionResponse;
import com.sbarrasa.extractor.service.PropsExtractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/attributes")
public class PropsExtractController {
    
    private static final Logger log = LoggerFactory.getLogger(PropsExtractController.class);
    
    private final PropsExtractService propsExtractService;

    @Autowired
    public PropsExtractController(PropsExtractService propsExtractService) {
        this.propsExtractService = propsExtractService;
    }

    @PostMapping(value = "/extract-text", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TextExtractionResponse> extractText(
            @RequestParam("file") MultipartFile file) {
        
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(
                new TextExtractionResponse("Error: No se ha subido ningún archivo", null));
        }
        
        try {
            log.info("Received file: {}, size: {} bytes, content-type: {}", 
                    file.getOriginalFilename(), file.getSize(), file.getContentType());
            
            String extractedText = propsExtractService.extractText(file);
            
            return ResponseEntity.ok(new TextExtractionResponse("Texto extraído con éxito", extractedText));
        } catch (IOException e) {
            log.error("Error processing file: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(
                new TextExtractionResponse("Error al procesar el archivo: " + e.getMessage(), null));
        }
    }
}
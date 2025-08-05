package com.sbarrasa.extractor.controller;

import com.sbarrasa.extractor.service.PropertySearchService;
import com.sbarrasa.extractor.service.TextExtractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/attributes")
public class PropsExtractController {
    private final TextExtractService textExtractService;
    private final PropertySearchService propertySearchService;

    @Autowired
    public PropsExtractController(TextExtractService textExtractService,
                                 PropertySearchService propertySearchService) {
        this.textExtractService = textExtractService;
        this.propertySearchService = propertySearchService;
    }

    @PostMapping(value = "/keys/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> extractWithKeys(
            @RequestParam("keys") Set<String> keys,
            @RequestParam("file") MultipartFile file) throws Exception {

        var extractedText = textExtractService.extract(file);
        return propertySearchService.findProperties(keys, extractedText);
    }

}
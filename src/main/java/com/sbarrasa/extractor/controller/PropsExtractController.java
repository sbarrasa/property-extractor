package com.sbarrasa.extractor.controller;

import com.sbarrasa.extractor.service.ProductTypesService;
import com.sbarrasa.extractor.service.PropertySearchService;
import com.sbarrasa.extractor.service.TextExtractService;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/attributes")
public class PropsExtractController {


    private final TextExtractService textExtractService;
    private final ProductTypesService productTypesService;
    private final PropertySearchService propertySearchService;

    @Autowired
    public PropsExtractController(TextExtractService textExtractService,
                                 ProductTypesService productTypesService,
                                 PropertySearchService propertySearchService) {
        this.textExtractService = textExtractService;
        this.productTypesService = productTypesService;
        this.propertySearchService = propertySearchService;
    }

    @PostMapping(value = "/properties/{productTypeId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> extractProperties(
            @PathVariable("productTypeId") Integer productTypeId,
            @RequestParam("file") MultipartFile file) throws IOException {

        var propertyKeys = productTypesService.get(productTypeId).getPropertyKeys();
        var extractedText = textExtractService.extract(file);
        return propertySearchService.findProperties(propertyKeys, extractedText);
    }

}
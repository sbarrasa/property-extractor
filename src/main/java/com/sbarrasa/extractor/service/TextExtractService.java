package com.sbarrasa.extractor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Log
public class TextExtractService {


    public String extract(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty())
            throw new IOException("No se ha recibido ningún archivo o el archivo está vacío");

        var contentType = file.getContentType();
        log.info("Processing file: %s, content type: %s, size: %s bytes"
                        .formatted(
                                file.getOriginalFilename(),
                                contentType,
                                file.getSize())
        );

        if (contentType != null
        && contentType.startsWith("image/"))
            return extractImageText(file);

        return extractTextFile(file);
    }

    private String extractTextFile(MultipartFile file) {
        log.info("Processing image file");
        return "Extracted text from image";
    }

    private String extractImageText(MultipartFile file) {
        log.info("Processing document file");
        return "This is a test document";
    }

}
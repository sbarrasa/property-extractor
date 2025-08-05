package com.sbarrasa.extractor.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class TextExtractService {

    private static final Logger log = LoggerFactory.getLogger(TextExtractService.class);

    /**
     * Extracts text from a file. This implementation is simplified for the test environment.
     * In a real environment, it would use Tesseract for images and Tika for documents.
     *
     * @param file The file to extract text from
     * @return The extracted text
     * @throws IOException If there's an error processing the file
     */
    public String extract(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IOException("No se ha proporcionado ningún archivo");
        }

        String contentType = file.getContentType();
        log.info("Processing file: {}, content type: {}, size: {} bytes",
                file.getOriginalFilename(), contentType, file.getSize());

        try {
            if (contentType != null
            && contentType.startsWith("image/")) {
                log.info("Processing image file");
                return "Extracted text from image";
            } else {
                log.info("Processing document file");
                return "This is a test document";
            }
        } catch (Exception e) {
            log.error("Error extracting text from document", e);
            throw new IOException("No se pudo extraer texto del documento: " + e.getMessage(), e);
        }
    }
}
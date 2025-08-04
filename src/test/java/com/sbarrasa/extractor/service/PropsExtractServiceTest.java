package com.sbarrasa.extractor.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PropsExtractServiceTest {

    @InjectMocks
    private PropsExtractService propsExtractService;

    private MockMultipartFile imageFile;
    private MockMultipartFile documentFile;
    private MockMultipartFile emptyFile;

    @BeforeEach
    void setUp() {
        imageFile = new MockMultipartFile(
                "image.jpg",
                "image.jpg",
                "image/jpeg",
                "test image content".getBytes()
        );

        documentFile = new MockMultipartFile(
                "document.pdf",
                "document.pdf",
                "application/pdf",
                "test document content".getBytes()
        );

        emptyFile = new MockMultipartFile(
                "empty.txt",
                "empty.txt",
                "text/plain",
                new byte[0]
        );
    }

    @Test
    void extractTextFromImageFile() throws IOException {
        String result = propsExtractService.extractText(imageFile);
        
        assertEquals("Extracted text from image", result);
    }

    @Test
    void extractTextFromDocumentFile() throws IOException {
        String result = propsExtractService.extractText(documentFile);
        
        assertEquals("This is a test document", result);
    }

    @Test
    void extractTextFromEmptyFile() {
        IOException exception = assertThrows(IOException.class,
                () -> propsExtractService.extractText(emptyFile));
        
        assertEquals("No se ha proporcionado ningún archivo", exception.getMessage());
    }

    @Test
    void extractTextFromNullFile() {
        IOException exception = assertThrows(IOException.class,
                () -> propsExtractService.extractText(null));
        
        assertEquals("No se ha proporcionado ningún archivo", exception.getMessage());
    }
}
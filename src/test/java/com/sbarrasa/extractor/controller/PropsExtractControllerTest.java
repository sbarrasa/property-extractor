package com.sbarrasa.extractor.controller;

import com.sbarrasa.extractor.dto.TextExtractionResponse;
import com.sbarrasa.extractor.service.PropsExtractService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PropsExtractControllerTest {

    @Mock
    private PropsExtractService propsExtractService;

    @InjectMocks
    private PropsExtractController controller;

    private MockMultipartFile validFile;
    private MockMultipartFile emptyFile;

    @BeforeEach
    void setUp() {
        validFile = new MockMultipartFile(
                "file",
                "test.pdf",
                "application/pdf",
                "test content".getBytes()
        );

        emptyFile = new MockMultipartFile(
                "file",
                "empty.txt",
                "text/plain",
                new byte[0]
        );
    }

    @Test
    void extractTextWithValidFile() throws IOException {
        // Arrange
        String extractedText = "Extracted text from file";
        when(propsExtractService.extractText(validFile)).thenReturn(extractedText);

        // Act
        ResponseEntity<TextExtractionResponse> response = controller.extractText(validFile);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Texto extraído con éxito", response.getBody().getMessage());
        assertEquals(extractedText, response.getBody().getExtractedText());
        verify(propsExtractService, times(1)).extractText(validFile);
    }

    @Test
    void extractTextWithEmptyFile() {
        // Act
        ResponseEntity<TextExtractionResponse> response = controller.extractText(emptyFile);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Error: No se ha subido ningún archivo", response.getBody().getMessage());
        assertNull(response.getBody().getExtractedText());
        verifyNoInteractions(propsExtractService);
    }

    @Test
    void extractTextWithServiceException() throws IOException {
        // Arrange
        String errorMessage = "Error processing file";
        when(propsExtractService.extractText(validFile)).thenThrow(new IOException(errorMessage));

        // Act
        ResponseEntity<TextExtractionResponse> response = controller.extractText(validFile);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Error al procesar el archivo: " + errorMessage, response.getBody().getMessage());
        assertNull(response.getBody().getExtractedText());
        verify(propsExtractService, times(1)).extractText(validFile);
    }
}
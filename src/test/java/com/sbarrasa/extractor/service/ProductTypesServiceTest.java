package com.sbarrasa.extractor.service;

import com.sbarrasa.extractor.entity.ProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProductTypesServiceTest {

    private ProductTypesService productTypesService;


    @Test
    void testGetAll() {
        var result = ProductTypesService.getAll();
        
        assertNotNull(result);
        assertEquals(3, result.size());

    }

    @Test
    void testGet_ValidProductTypeId() {
        var bookProperties = ProductTypesService.get(1);
        Set<String> electronicsProperties = productTypesService.get(2);
        Set<String> clothingProperties = productTypesService.get(3);
        
        // Then
        assertEquals(Set.of("title", "author", "isbn"), bookProperties);
        assertEquals(Set.of("brand", "model", "warranty"), electronicsProperties);
        assertEquals(Set.of("size", "color", "material"), clothingProperties);
    }

    @Test
    void testGet_InvalidProductTypeId() {
        // When & Then
        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> productTypesService.get(999)
        );
        
        assertEquals("No hay un producto con Id: 999", exception.getMessage());
    }

    @Test
    void testProductTypesInitializedOnlyOnce() {
        // When
        Set<ProductType> firstCall = productTypesService.getAll();
        Set<ProductType> secondCall = productTypesService.getAll();
        
        // Then
        assertSame(firstCall, secondCall, "The same instance should be returned on subsequent calls");
    }
}
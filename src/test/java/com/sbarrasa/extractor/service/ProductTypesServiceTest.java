package com.sbarrasa.extractor.service;

import com.sbarrasa.extractor.entity.ProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProductTypesServiceTest {

    private ProductTypesService productTypesService = new ProductTypesService(
        Set.of(
            new ProductType(1, "Book", Set.of("title", "author", "isbn")),
            new ProductType(2, "Electronics", Set.of("brand", "model", "warranty")),
            new ProductType(3, "Clothing", Set.of("size", "color", "material"))
        )
    );


    @Test
    void testGetAll() {
        var result = productTypesService.getProductTypes();
        
        assertNotNull(result);
        assertEquals(3, result.size());

    }

    @Test
    void testGet_ValidProductTypeId() {
        var productType1 = productTypesService.get(1).getPropertyKeys();
        var productType2 = productTypesService.get(2).getPropertyKeys();

        assertEquals(Set.of("title", "author", "isbn"), productType1 );
        assertEquals(Set.of("brand", "model", "warranty"), productType2);
    }

    @Test
    void testGet_InvalidProductTypeId() {
        var id = 999;
        var exception = assertThrows(
            RuntimeException.class,
            () -> productTypesService.get(id)
        );
        
        assertEquals("No hay un producto con Id: "+id, exception.getMessage());
    }


}
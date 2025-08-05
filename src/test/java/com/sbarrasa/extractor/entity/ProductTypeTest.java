package com.sbarrasa.extractor.entity;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProductTypeTest {
    final ProductType productType1 = new ProductType(1, "Product Type 1");
    final ProductType productType2 = new ProductType(1, "Different Name");
    final ProductType productType3 = new ProductType(2, "Product Type 1");

    @Test
    void testProductType() {
        assertEquals(1, productType1.getId());
        assertEquals("Product Type 1", productType1.getName());
        assertTrue(productType1.getPropertyKeys().isEmpty());
    }

    @Test
    void testEquals() {
        assertEquals(productType1, productType2);
        assertNotEquals(productType1, productType3);
    }



    @Test
    void testAddPropertyKey() {
        var productType = new ProductType(4, "Test Type");
        productType.addPropertyKey("color");
        productType.addPropertyKey("size");
        
        assertTrue(productType.getPropertyKeys().contains("color"));
        assertEquals(2, productType.getPropertyKeys().size());
    }

}
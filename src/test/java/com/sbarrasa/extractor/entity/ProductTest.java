package com.sbarrasa.extractor.entity;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    final Product product1 = new Product(1, "Test Product",
            Map.of("key1", "value1",
                    "key2", 123));
    final Product product2 = new Product(2, "Another Product",
            Map.of("keyA", "valueA",
                    "keyB", 456));

    final Product productA = new Product(1, "Product A");

    @Test
    void testProduct() {
        assertEquals(1, product1.getId());
        assertEquals("Test Product", product1.getName());
        assertEquals("value1", product1.getProperty("key1"));
        assertEquals(123, product1.getProperty("key2"));
    }

    @Test
    void testEquals() {

        assertEquals(product1, productA);

        assertNotEquals(product2, product1);
    }

    @Test
    void testCreateFromOtherProduct() {
        var productX = new Product(product1);

        assertEquals(productX, product1);

        assertEquals(123, productX.getProperty("key2"));

    }

    @Test
    void testCreateFromProductType() {
        var productType = new ProductType(100,
                "Product100",
                Set.of("key1", "key2", "key3"));

        var productB = new Product(productType);

        assertEquals(productB, productType);

        assertEquals(3, productB.getProperties().size());

    }



}
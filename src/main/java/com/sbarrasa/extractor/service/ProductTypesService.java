package com.sbarrasa.extractor.service;

import com.sbarrasa.extractor.entity.ProductType;
import lombok.Data;
import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Log
@Data
public class ProductTypesService {

    private final Set<ProductType> productTypes;

    public ProductTypesService() {
       this.productTypes = new HashSet<>(defaultProductTypes());
    }

    public Set<String> getPropertyKeys(Integer productTypeId) {
       return get(productTypeId).getPropertyKeys();
    }

    public ProductType get(String productName) {
        return productTypes
                .stream()
                .filter(pt -> pt.getName().equals(productName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No hay un producto con nombre: %s".formatted(productName)));

    }

    public ProductType get(Integer productTypeId) {
        return productTypes
                .stream()
                .filter(pt -> pt.getId().equals(productTypeId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No hay un producto con Id: %s".formatted(productTypeId)));

    }

    private static Set<ProductType> defaultProductTypes() {
        return Set.of(
                new ProductType(1, "Book",
                        Set.of("title", "author", "isbn")),
                new ProductType(2, "Electronics",
                        Set.of("brand", "model", "warranty")),
                new ProductType(3, "Clothing",
                        Set.of("size", "color", "material"))
        );
    }


}
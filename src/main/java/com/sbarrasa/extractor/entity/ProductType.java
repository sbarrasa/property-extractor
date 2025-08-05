package com.sbarrasa.extractor.entity;

import lombok.Getter;
import java.util.HashSet;
import java.util.Set;

@Getter
public class ProductType extends AbstractProduct {
    
    private final Set<String> propertyKeys;


    public ProductType(Integer id, String name) {
        this(id, name, new HashSet<>());
    }
    
    public ProductType(Integer id, String name, Set<String> propertyKeys) {
        super(id, name);
        this.propertyKeys = propertyKeys != null ? propertyKeys : new HashSet<>();
    }
    
    public ProductType addPropertyKey(String key) {
        propertyKeys.add(key);
        return this;
    }

    


    

}
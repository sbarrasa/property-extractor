package com.sbarrasa.extractor.entity;


import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
public class Product extends AbstractProduct {
    private final Map<String, Object> properties;

    public Product(Integer id, String name) {
        this(id, name, new HashMap<>());
    }
    public Product(Integer id, String name, Map<String, Object> properties) {
        super(id, name);
        this.properties = properties;
    }

    public Product(Product product) {
        super(product);
        this.properties = product.getProperties();
    }

    public Product(AbstractProduct product) {
        super(product);
        this.properties = new HashMap<>();
        product.getPropertyKeys()
                .forEach(k -> properties.put(k, null));
    }

    @Override
    public Set<String> getPropertyKeys() {
        return properties.keySet();
    }

    public Object getProperty(String key) {
        return properties.get(key);
    }

    public Product setProperty(String key, Object value) {
        properties.put(key, value);
        return this;
    }
}
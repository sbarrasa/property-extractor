package com.sbarrasa.extractor.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@EqualsAndHashCode(of = "id")
public abstract class AbstractProduct {
    private final Integer id;
    @Setter
    private final String name;
    
    public abstract Set<String> getPropertyKeys();

    public AbstractProduct(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public AbstractProduct(AbstractProduct product) {
        this.id = product.getId();
        this.name  = product.getName();
    }
}
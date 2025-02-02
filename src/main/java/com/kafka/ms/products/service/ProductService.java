package com.kafka.ms.products.service;

import com.kafka.ms.products.model.CreateProductRequest;

public interface ProductService {
    String createProduct(CreateProductRequest product);
}

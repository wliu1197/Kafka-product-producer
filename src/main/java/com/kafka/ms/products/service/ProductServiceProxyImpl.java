package com.kafka.ms.products.service;

import com.kafka.ms.products.model.CreateProductRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("ProductServiceProxy")
public class ProductServiceProxyImpl implements ProductService {
    @Override
    public String createProduct(CreateProductRequest product){
        return "success";
    }
}

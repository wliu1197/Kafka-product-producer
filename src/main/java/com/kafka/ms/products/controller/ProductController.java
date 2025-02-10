package com.kafka.ms.products.controller;

import com.kafka.ms.products.model.CreateProductRequest;
import com.kafka.ms.products.service.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class ProductController {
    ProductService productService;

    public ProductController(@Qualifier("ProductServiceImpl") ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    public ResponseEntity<String> addProduct(@RequestBody CreateProductRequest product) {
        String productId = productService.createProductTransaction(product);
        return new ResponseEntity<String>(productId,HttpStatus.CREATED);
    }
}

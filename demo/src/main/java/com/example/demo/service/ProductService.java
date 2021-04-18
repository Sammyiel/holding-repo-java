package com.example.demo.service;

import com.example.demo.data.ProductData;
import com.example.demo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductData productData;


    @Autowired
    public ProductService(@Qualifier("fakedata") ProductData productData){
        this.productData = productData;
    }

    public int addProduct(@RequestBody Product product){
        return productData.insertProduct(product);
    }

    public List<Product> getAllProducts() {
        return productData.selectAllProducts();
    }

    public Optional<Product> getProductById(UUID id){
        return productData.selectProductById(id);
    }



}

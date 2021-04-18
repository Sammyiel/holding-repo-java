package com.example.demo.api;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/product")
@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController (ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public void addProduct(Product product){
        productService.addProduct(product);
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }
    @GetMapping(path = "{id}")
    public Product getProductById(@PathVariable("id") UUID id){
        return productService.getProductById(id)
                .orElse(null);

    }
}

package com.example.demo.api;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import com.sun.istack.NotNull;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
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
    public void addProduct(@NonNull @RequestBody Product product){
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
    @DeleteMapping(path = "{id}")
    public void deleteProductById(@PathVariable("id") UUID id){
        productService.deleteProduct(id);
    }

    @PutMapping(path = "{id}")
    public void updateProduct(@PathVariable("id") UUID id, @RequestBody Product productToUpdate){
        productService.updateProduct(id, productToUpdate);
    }
}

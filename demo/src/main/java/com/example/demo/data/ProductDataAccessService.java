package com.example.demo.data;

import com.example.demo.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakedata")
public class ProductDataAccessService implements  ProductData{

    private static List<Product> DB = new ArrayList<>();

    @Override
    public int insertProduct(UUID id, Product product) {
        DB.add(new Product(id, product.getName(), product.getPrice(), product.getQuantity()));
        return 1;
    }

    public List<Product> selectAllProducts() {
        return DB;
    }

    @Override
    public Optional<Product> selectProductById(UUID id) {
        return DB.stream()
               .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deleteProductById(UUID id) {
        Optional<Product> productMaybe=selectProductById(id);
        if(productMaybe.isEmpty()){
            return 0;
        }
        DB.remove(productMaybe.get());
        return 1;
    }

    @Override
    public int updateProductById(UUID id, Product update) {
        return selectProductById(id)
                .map(product -> {
                    int indexOfProductToUpdate = DB.indexOf(product);
                        if(indexOfProductToUpdate >= 0) {
                            DB.set(indexOfProductToUpdate, new Product(id, update.getName(), update.getPrice(), update.getQuantity()));
                            return 1;
                        }
                        return 0;
                })
                .orElse(0);

    }
}

package com.example.demo;

import com.example.demo.data.ProductDataAccessService;
import com.example.demo.model.Product;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class ProductDataAccessServiceTest {
    private ProductDataAccessService underTest;

    @Before("fakedata")
    public void setUp(){

        underTest = new ProductDataAccessService();
    }

    @Test
    public void canPerformCRUD(){
        //Given a Product by the name rosemary leaves
        UUID IdOne = UUID.randomUUID();
        Product productOne = new Product(IdOne, "Rosemary Leaves", 1500.00, "50g");

        //given another product by the name Ginger Tea
        UUID IdTwo = UUID.randomUUID();
        Product productTwo = new Product(IdTwo, "Ginger Tea", 2500.00, "70g");

        // when both products are added to db
        underTest.insertProduct(IdOne, productOne);
        underTest.insertProduct(IdTwo, productTwo);

        // then we are able to retrieve records of the product Rosemary Leaves
        assertThat(underTest.selectProductById(IdOne))
                .isPresent()
                .hasValueSatisfying(productFromDB -> assertThat(productFromDB).isEqualTo(productOne));

        // also we are able to retrieve records of Ginger Tea
        assertThat(underTest.selectProductById(IdOne))
                .isPresent()
                .hasValueSatisfying(productFromDB -> assertThat(productFromDB).isEqualTo(productTwo));

        // Then we are also able to retrieve all products
        List<Product> products = underTest.selectAllProducts();


        // ...List should have size 2 and should have both James and Anna
        assertThat(products)
                .hasSize(2)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(productOne, productTwo);

        // ... An update request (James Bond name to Jake Black)
        Product productToUpdate = new Product(IdOne, "Rosemary", 1500.00, "50g");

        // When Update
        assertThat(underTest.updateProductById(IdOne, productToUpdate)).isEqualTo(1);

        // Then when get person with idOne then should have name as James Bond > Jake Black
        assertThat(underTest.selectProductById(IdOne))
                .isPresent()
                .hasValueSatisfying(personFromDb -> assertThat(personFromDb).isEqualToComparingFieldByField(productToUpdate));

        // When Delete Jake Black
        assertThat(underTest.deleteProductById(IdOne)).isEqualTo(1);

        // When get personOne should be empty
        assertThat(underTest.selectProductById(IdOne)).isEmpty();

        // Finally DB should only contain only Anna Smith
        assertThat(underTest.selectAllProducts())
                .hasSize(1)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(productTwo);


    }

}



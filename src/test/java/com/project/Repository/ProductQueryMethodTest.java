package com.project.Repository;

import com.project.Entity.Product;
import com.project.Entity.Seller;
import com.project.Enum.ProductStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.project.Enum.ProductCategory.ELECTRONICS;

@SpringBootTest
public class ProductQueryMethodTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Test
    void findAllByCategoryMethod(){

        List<Product> productList = productRepository.findAllByCategory(ELECTRONICS);

        productList.forEach((p) ->{

            System.out.println(p.getName());
            System.out.println(p.getPrice());
            System.out.println(p.getProductStatus());

        });
    }

    @Test
    void findMethod(){

        Product product = productRepository.findFirstByOrderByPriceAsc();

        System.out.println(product.getName());
        System.out.println(product.getPrice());
    }

    @Test
    void findAllProductSortByNameMethod(){


        List<Product> productList = productRepository.findAllProducts(Sort.by(Sort.Direction.DESC,"name"));
//        List<Product> productList = productRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        productList.forEach((p) -> {
            System.out.println(p.getName());
        });
    }


    @Test
    void findAllOutOfStockProducts(){

        List<Product> productList = productRepository.findAllOutOfStockProducts(String.valueOf(ProductStatus.OUT_OF_STOCK));

        productList.forEach((product -> {

            System.out.println(product.getName());

        }));



    }
}

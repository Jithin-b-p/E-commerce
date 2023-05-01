package com.project.Repository;

import com.project.Entity.Product;
import com.project.Enum.ProductCategory;
import com.project.Enum.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByCategory(ProductCategory category);

    Product findFirstByOrderByPriceAsc();

    @Query(value = "SELECT p FROM Product p")
    List<Product> findAllProducts(Sort sort);

    List<Product> findTop5ByOrderByPriceAsc();

    List<Product> findTop5ByOrderByPriceDesc();

    @Query(value = "SELECT * FROM PRODUCT p WHERE p.product_status = ?1",nativeQuery = true)
    List<Product> findAllOutOfStockProducts(String productStatus);



}

package com.project.Repository;

import com.project.Entity.Product;
import com.project.Enum.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    public List<Product> findAllByCategory(ProductCategory category);
}

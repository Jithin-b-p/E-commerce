package com.project.Repository;


import com.project.Entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Integer> {

    public Seller findByEmail(String email);
}

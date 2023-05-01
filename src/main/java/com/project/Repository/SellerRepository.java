package com.project.Repository;


import com.project.Entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Integer> {

    public Seller findByEmail(String email);

    public List<Seller> findByAgeLessThan(int age);
}



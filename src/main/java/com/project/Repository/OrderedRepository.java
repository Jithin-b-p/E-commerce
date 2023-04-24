package com.project.Repository;

import com.project.Entity.Ordered;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderedRepository extends JpaRepository<Ordered, Integer> {
}

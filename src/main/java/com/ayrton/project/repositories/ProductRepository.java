package com.ayrton.project.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayrton.project.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	Optional<Product> findByName(String name);
}

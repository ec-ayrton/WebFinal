package com.ayrton.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayrton.project.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	
}

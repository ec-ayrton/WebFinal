package com.ayrton.project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayrton.project.entities.Product;
import com.ayrton.project.repositories.ProductRepository;
import com.ayrton.project.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	public List<Product> findAll(){
		return repository.findAll();
	}
	
	public Product findById(Long id) {
		Optional<Product> c = repository.findById(id);
		return c.orElseThrow( ()-> new ResourceNotFoundException(id));
	}
	
	public Product insert(Product p) {
		return repository.save(p);
	}
	
	
}

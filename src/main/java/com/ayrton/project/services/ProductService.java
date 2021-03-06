package com.ayrton.project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayrton.project.entities.Product;
import com.ayrton.project.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	public List<Product> findAll(){
		return repository.findAll();
	}
	
	public Optional<Product> findById(Long id) {
		Optional<Product> c = repository.findById(id);
		return c;
	}
	
	public Product insert(Product p) {
		List<Product> products= repository.findAll();
		for(Product product : products) {
			if(product.equals(p)) {
				return null;
			}
		}
		return repository.save(p);
	}

}

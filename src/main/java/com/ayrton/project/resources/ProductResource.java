package com.ayrton.project.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ayrton.project.entities.Product;
import com.ayrton.project.services.ProductService;

@RestController
@RequestMapping(value = "/produtos")
public class ProductResource {
	
	@Autowired
	private ProductService service;
	
	@GetMapping
	public ResponseEntity<List<Product>> findAll(){
		List<Product> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> findById(@PathVariable Long id){
		Product p = service.findById(id);
		return ResponseEntity.ok().body(p);
	}
	
	@PostMapping
	public ResponseEntity<Product> insert(@RequestBody Product p){
		p = service.insert(p);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(p.getId()).toUri();
		return ResponseEntity.created(uri).body(p);	
	}
}

package com.ayrton.project.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<Object> findById(@PathVariable Long id){
		Optional<Product> productOpt = service.findById(id);
		if(productOpt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto nao encontrado!");
		}else {
			return ResponseEntity.ok().body(productOpt.get());
		}
	}
	@PostMapping
	public ResponseEntity<Object> insert(@RequestBody Product p){
		
		Product produtoSaved = service.insert(p);
		if(produtoSaved == null ){
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro ao cadastrar Produto.");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("O produto "+p.getName()+" "+p.getDescription()+" foi cadastrado com sucesso!");	
	}
}

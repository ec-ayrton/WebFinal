package com.ayrton.project.resources;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

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

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/produtos")
public class ProductResource {
	
	@Autowired
	private ProductService service;
	
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Retorna a lista de produtos."),

		})
	@GetMapping
	public ResponseEntity<List<Product>> findAll(){
		List<Product> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Retorna um produto."),
		    @ApiResponse(code = 404, message = "produto nao encontrado!"),
		})
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id){
		Optional<Product> productOpt = service.findById(id);
		if(productOpt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto nao encontrado!");
		}else {
			return ResponseEntity.ok().body(productOpt.get());
		}
	}
	@ApiResponses(value = {
		    @ApiResponse(code = 201, message = "O produto foi cadastrado com sucesso!"),
		    @ApiResponse(code = 409, message = "Erro ao cadastrar Produto."),
		})
	@Transactional
	@PostMapping
	public ResponseEntity<Object> insert(@RequestBody Product p){
		
		Product produtoSaved = service.insert(p);
		if(produtoSaved == null ){
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro ao cadastrar Produto.");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("O produto foi cadastrado com sucesso!");	
	}
}

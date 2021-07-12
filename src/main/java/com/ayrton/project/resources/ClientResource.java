package com.ayrton.project.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ayrton.project.entities.Client;
import com.ayrton.project.services.ClientService;

@RestController
@RequestMapping(value = "/clientes")
public class ClientResource {
	
	@Autowired
	private ClientService service;
	
	@GetMapping
	public ResponseEntity<List<Client>> findAll(){
		List<Client> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	@GetMapping(value = "/{id}")
	public ResponseEntity<Client> findById(@PathVariable Long id){
		Client c = service.findById(id);
		return ResponseEntity.ok().body(c);
	}
	@PostMapping
	public ResponseEntity<Client> insert(@RequestBody Client c){
		c = service.insert(c);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(c.getId()).toUri();
		return ResponseEntity.created(uri).body(c);	
	}
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}

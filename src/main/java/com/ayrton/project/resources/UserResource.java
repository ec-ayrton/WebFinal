package com.ayrton.project.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayrton.project.entities.Client;

@RestController
@RequestMapping(value = "/clientes")
public class UserResource {
	
	@GetMapping
	public ResponseEntity<Client> findAll(){
		Client u = new Client(1L,"ayrton","00011122233","889900112233");
		return ResponseEntity.ok().body(u);
	}
}

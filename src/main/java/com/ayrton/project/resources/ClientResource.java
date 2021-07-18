package com.ayrton.project.resources;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayrton.project.entities.Client;
import com.ayrton.project.entities.DTO.ClientForm;
import com.ayrton.project.entities.DTO.ClientResponse;
import com.ayrton.project.services.ClientService;

@RestController
@RequestMapping(value = "/clientes")
public class ClientResource {
	
	@Autowired
	private ClientService service;
	
	@GetMapping
	public ResponseEntity<List<ClientResponse>> findAll(){
		List<Client> list = service.findAll();
		List<ClientResponse> listResponse = new ArrayList<>();
		for(Client client:list) {
			listResponse.add(client.ToResponse());
		}
		return ResponseEntity.ok().body(listResponse);
	}
	@GetMapping(value = "/{id}")
	public ResponseEntity<ClientResponse> findById(@PathVariable Long id){
		Client client = service.findById(id);
		ClientResponse clientResponse = client.ToResponse();
		return ResponseEntity.ok().body(clientResponse);
	}
	@PostMapping
	public ResponseEntity<Object> insert(@RequestBody @Valid ClientForm clientForm){
		Client client = clientForm.toModel();
		Client clientSaved = service.insert(client);
		if(clientSaved == null ){
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro ao cadastrar cliente.");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body( "O cliente "+clientSaved.getName()+" foi cadastrado com sucesso !");            	
	}
	//
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}

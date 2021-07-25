package com.ayrton.project.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayrton.project.entities.Client;
import com.ayrton.project.entities.DTO.ClientForm;
import com.ayrton.project.entities.DTO.ClientResponse;
import com.ayrton.project.services.ClientService;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/clientes")
public class ClientResource {

	@Autowired
	private ClientService service;

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna a lista de clientes"), })
	@GetMapping
	public ResponseEntity<List<ClientResponse>> findAll() {
		List<Client> list = service.findAll();
		List<ClientResponse> listResponse = new ArrayList<>();
		for (Client client : list) {
			listResponse.add(client.ToResponse());
		}
		return ResponseEntity.ok().body(listResponse);
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna um cliente"),
			@ApiResponse(code = 404, message = "Cliente não encontrado."), })
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id) {
		Optional<Client> clientOpt = service.findById(id);
		if (clientOpt.isPresent()) {
			ClientResponse clientResponse = clientOpt.get().ToResponse();
			return ResponseEntity.ok().body(clientResponse);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente nao encontrado!");
		}
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna um cliente"),
			@ApiResponse(code = 404, message = "Cliente não encontrado."), })
	
	@GetMapping(value = "/detalhes/{id}")
	public ResponseEntity<Object> findClientDetailsById(@PathVariable Long id) {
		Optional<Client> clientOpt = service.findById(id);
		if (clientOpt.isPresent()) {
			return ResponseEntity.ok().body(clientOpt.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente nao encontrado!");
		}
	}
	@ApiResponses(value = {
		    @ApiResponse(code = 201, message = "Cliente cadastrado com sucesso."),
		    @ApiResponse(code = 409, message = "Erro ao cadastrar cliente."),
		})
	@Transactional
	@PostMapping
	public ResponseEntity<Object> insert(@RequestBody @Valid ClientForm clientForm) {

		Client client = clientForm.toModel();
		ClientResponse clientSaved = service.insert(client);

		if (clientSaved == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro ao cadastrar cliente.");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("O cliente foi cadastrado com sucesso !");
	}

	//
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Cliente atualizado com sucesso"),
		    @ApiResponse(code = 404, message = "Cliente não encontrado."),
	})
	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> update(@PathVariable long id, @RequestBody @Valid ClientForm clientForm) {
		Client client = clientForm.toModel();
		boolean status = service.update(id, client);
		if (status) {
			return ResponseEntity.status(HttpStatus.OK).body("Cliente alterado com sucesso.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente nao encontrado!");
		}
	}

	//
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Cliente deletado com sucesso"),
		    @ApiResponse(code = 200, message = "Erro ao apagar registro do cliente."),
		    @ApiResponse(code = 404, message = "Cliente não encontrado."),
		})
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		int status = service.delete(id);
		if (status == 200) {
			return ResponseEntity.status(HttpStatus.OK).body("O cliente foi deletado com sucesso.");
		} else if (status == 404) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao apagar registro do cliente.");
		}
	}
}

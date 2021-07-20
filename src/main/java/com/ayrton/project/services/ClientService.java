package com.ayrton.project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ayrton.project.entities.Client;
import com.ayrton.project.entities.DTO.ClientResponse;
import com.ayrton.project.repositories.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;
	
	public List<Client> findAll(){
		return repository.findAll();
	}
	public Optional<Client> findById(Long id) {
		Optional<Client> c = repository.findById(id);
		return c;
	}
	public Optional<Client>findByCPF(String cpf){
		Optional<Client> client = repository.findByCPF(cpf);
		return client;
	}
	
	public ClientResponse insert(Client c) {
		
		Optional<Client> clientOpt = repository.findByCPF(c.getCPF());
		
		if(clientOpt.isEmpty()) {
			c = repository.save(c);
			ClientResponse clientSaved = c.ToResponse();
			
			return clientSaved;
		}else {
			return null;
		}
	}
	public Object delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			return 404;
		} catch (DataIntegrityViolationException e) {
			// TODO: handle exception
			return 400;
		}
		return 200;
	}
	
}

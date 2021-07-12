package com.ayrton.project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayrton.project.entities.Client;
import com.ayrton.project.repositories.ClientRepository;
import com.ayrton.project.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;
	
	public List<Client> findAll(){
		return repository.findAll();
	}
	public Client findById(Long id) {
		Optional<Client> c = repository.findById(id);
		return c.orElseThrow( ()-> new ResourceNotFoundException(id));
	}
}

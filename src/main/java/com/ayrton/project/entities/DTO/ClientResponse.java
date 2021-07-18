package com.ayrton.project.entities.DTO;

import com.ayrton.project.entities.Client;

public class ClientResponse {
	private long id;
	private String nome;
	
	public ClientResponse(long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public ClientResponse(Client clientSaved) {
		this.id=clientSaved.getId();
		this.nome=clientSaved.getName();
	}

	public long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	
}

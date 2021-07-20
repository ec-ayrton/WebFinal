package com.ayrton.project.entities.DTO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.OneToMany;

import com.ayrton.project.entities.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ClientResponse {
	private long id;
	private String nome;
	
	@JsonIgnore
	@OneToMany(mappedBy = "client")
	private List<Order> orders = new ArrayList<>();
	
	public ClientResponse(long id, String nome, List<Order> orders2) {
		super();
		this.id = id;
		this.nome = nome;
		this.orders=orders2;
	}

	public long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	public List<Order> getOrders() {
		return orders;
	}	

}

package com.ayrton.project.entities.DTO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.OneToMany;

import com.ayrton.project.entities.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ClientResponse {
	private long id;
	private String name;
	
	@JsonIgnore
	@OneToMany(mappedBy = "client")
	private List<Order> orders = new ArrayList<>();
	
	public ClientResponse(long id, String name, List<Order> orders2) {
		super();
		this.id = id;
		this.name = name;
		this.orders=orders2;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	public List<Order> getOrders() {
		return orders;
	}

	@Override
	public String toString() {
		return "ClientResponse [id=" + id + ", nome=" + name + "]";
	}	

	
}

package com.ayrton.project.entities.DTO;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.OneToMany;

import com.ayrton.project.entities.OrderItem;

public class OrderResponse {

	private long id;
	
	private LocalDate dataPedido;
	
	private ClientResponse clientResponse;
	
	@OneToMany(mappedBy = "id.order")
	private Set<OrderItem> items = new HashSet<>();
	
	public OrderResponse() {
		super();
	}

	public OrderResponse(long id, LocalDate dataPedido, ClientResponse toResponse, Set<OrderItem> items ) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.dataPedido =dataPedido;
		this.clientResponse=toResponse;
		this.items=items;
	}
	
	public long getId() {
		return id;
	}
	public LocalDate getDataPedido() {
		return dataPedido;
	}

	public ClientResponse getClientResponse() {
		return clientResponse;
	}

	public Set<OrderItem> getItems() {
		return items;
	}
	public Double getTotal() {
		double total = 0.0;
		for(OrderItem x: items) {
			total = total + x.getSubTotal();
		}
		return total;
	}
}

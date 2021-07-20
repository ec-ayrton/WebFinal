package com.ayrton.project.entities.DTO;

import java.time.LocalDate;
import java.util.Optional;

import com.ayrton.project.entities.Order;
import com.ayrton.project.entities.OrderItem;
import com.ayrton.project.entities.Product;


public class OrderItemForm {
	
	private LocalDate dataPedido;
	private String cpf;
	private long idProduto;
	private int quantidadeProduto;
	
	
	public OrderItemForm() {
		super();
	}

	public OrderItemForm(LocalDate dataPedido, String cpf, long idProduto, int quantidadeProduto) {
		super();
		this.dataPedido = dataPedido;
		this.cpf = cpf;
		this.idProduto = idProduto;
		this.quantidadeProduto = quantidadeProduto;
	}

	public LocalDate getDataPedido() {
		return dataPedido;
	}

	public String getCpf() {
		return cpf;
	}

	public long getIdProduto() {
		return idProduto;
	}

	public int getQuantidadeProduto() {
		return quantidadeProduto;
	}

	public  OrderItem toModel(Order orderFromDb, Optional<Product> productFromDb, int i) {
		return new OrderItem(orderFromDb, productFromDb.get(), i);
	}
	
	
}

package com.ayrton.project.entities.DTO;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import com.ayrton.project.entities.Client;
import com.ayrton.project.entities.Order;

public class OrderForm {
	
	
	private LocalDate dataPedido;
	
	@NotNull
	@CPF
	private String cpf;
	
	public OrderForm() {
		super();
	}

	public OrderForm(LocalDate dataPedido,  String cpf) {
		super();
		this.dataPedido = dataPedido;
		this.cpf=cpf;
	}

	public LocalDate getDataPedido() {
		return dataPedido;
	}

	public String getCPF() {
		return this.cpf;
	}
	
	@Override
	public String toString() {
		return "OrderForm [dataPedido=" + dataPedido + ", CPFClient=" + cpf + "]";
	}

	public Order toModel(Client client) {
		
		return new Order(this.dataPedido, client);
	}	
}

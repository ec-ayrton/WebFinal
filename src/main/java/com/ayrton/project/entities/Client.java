package com.ayrton.project.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="tb_client")
public class Client implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String CPF;
	private String fone;
	
	@OneToMany(mappedBy = "client")
	private List<Order> orders = new ArrayList<>();
	
	
	public Client() {
		super();
	}

	public Client(Long id, String nome, String cPF, String fone) {
		super();
		this.id = id;
		this.name = nome;
		CPF = cPF;
		this.fone = fone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return name;
	}

	public void setNome(String nome) {
		this.name = nome;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}
	public List<Order> getOrders() {
		return orders;
	}
	@Override
	public int hashCode() {
		return Objects.hash(CPF, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return Objects.equals(CPF, other.CPF) && Objects.equals(id, other.id);
	}

	
	
	
}

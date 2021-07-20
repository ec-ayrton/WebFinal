package com.ayrton.project.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.ayrton.project.entities.DTO.ClientResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tb_client")
public class Client implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank
	@Column(nullable = false, length = 30)
	private String name;
	
	@NotBlank
	@Column(unique = true, length = 11)
	private String CPF;
	
	@Column(length = 10)
	public String fone;
	
	@JsonIgnore
	@OneToMany(mappedBy = "client")
	private List<Order> orders = new ArrayList<>();
	
	
	public Client() {
		super();
	}

	public Client( String name, String CPF, String fone) {
		super();
		this.name = name;
		this.CPF = CPF;
		this.fone = fone;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setNome(String name) {
		this.name = name;
	}

	public String getCPF() {
		return CPF;
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
		return Objects.hash(CPF, fone, id, name);
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
		return Objects.equals(CPF, other.CPF) && Objects.equals(fone, other.fone) && id == other.id
				&& Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", name=" + name + ", CPF=" + CPF + ", fone=" + fone + "]";
	}

	public ClientResponse ToResponse() {
		return new ClientResponse(this.id, this.name,this.orders);
	}
}

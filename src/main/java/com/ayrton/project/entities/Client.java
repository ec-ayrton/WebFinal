package com.ayrton.project.entities;

import java.io.Serializable;
import java.util.Objects;


public class Client implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nome;
	private String CPF;
	private String fone;
	
	public Client() {
		super();
	}

	public Client(Long id, String nome, String cPF, String fone) {
		super();
		this.id = id;
		this.nome = nome;
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
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

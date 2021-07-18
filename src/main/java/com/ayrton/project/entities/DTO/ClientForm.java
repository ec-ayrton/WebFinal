package com.ayrton.project.entities.DTO;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import com.ayrton.project.entities.Client;

public class ClientForm {
	@NotNull
	@Column(nullable = false, length = 30)
	private String name;
	@NotNull
	@CPF
	private String cpf;
	@NotNull
	@Size(min = 10, max=11)
	private String fone;
	
	public ClientForm() {
		super();
	}
	
	public ClientForm(@NotNull String name,  @NotNull @CPF String CPF,
			@NotNull @Size(min = 10, max = 11) String fone) {
		super();
		this.name = name;
		this.cpf = CPF;
		this.fone = fone;
	}
	
	public String getName() {
		return name;
	}
	public String getCPF() {
		return cpf;
	}
	public String getFone() {
		return fone;
	}
	
	public Client toModel() {
		return new Client(this.name,this.cpf,this.fone);
	}

	@Override
	public String toString() {
		return "ClientForm [name=" + name + ", CPF=" + cpf + ", fone=" + fone + "]";
	}
}

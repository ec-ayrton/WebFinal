package com.ayrton.project.entities.DTO;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import com.ayrton.project.entities.Client;

public class ClientForm {
	@NotBlank
	@Size(max = 50)
	@Column(nullable = false)
	private String name;
	@NotBlank
	@CPF
	@Column(unique = true, length = 11)
	private String cpf;
	@NotBlank
	@Column(length = 10)
	private String fone;
	
	public ClientForm() {
		super();
	}
	
	public ClientForm(@NotBlank String name,  @NotBlank  String CPF,
			@NotBlank String fone) {
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

	
}

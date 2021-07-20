package com.ayrton.project.services.exceptions;

public class CPFAlreadyExistsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CPFAlreadyExistsException(Object id) {
		super("CPF Already Registered.CPF: "+id);
	}
}
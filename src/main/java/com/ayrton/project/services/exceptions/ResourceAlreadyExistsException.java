package com.ayrton.project.services.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ResourceAlreadyExistsException() {
		super("Resource Already Exists.");
	}
}

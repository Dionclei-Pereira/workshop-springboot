package me.dionclei.workshopspringboot.services.exceptions;

public class TokenException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public TokenException(String message) {
		super(message);
	}
}

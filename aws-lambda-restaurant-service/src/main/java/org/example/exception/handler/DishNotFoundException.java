package org.example.exception.handler;

public class DishNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DishNotFoundException(String message) {
        super(message);
    }
}
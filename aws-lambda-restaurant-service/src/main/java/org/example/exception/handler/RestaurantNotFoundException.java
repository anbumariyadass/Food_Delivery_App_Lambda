package org.example.exception.handler;

public class RestaurantNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RestaurantNotFoundException(String message) {
        super(message);
    }
}

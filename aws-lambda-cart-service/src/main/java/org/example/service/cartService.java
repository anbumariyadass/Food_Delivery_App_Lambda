package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.example.entity.Cart;
import org.example.repository.CartRepository;


@Service
public class cartService {
	@Autowired
    private CartRepository cartRepository;
	
	public String addCart(Cart cart) {
		cartRepository.save(cart);
		return cart.getCartId() +" is added successfully";
	}
	
	public Cart getCart(String cartId) {
		return cartRepository.findById(cartId);
	}
	
	public String updateCart(Cart cart) {
		cartRepository.save(cart);
		return cart.getCartId() +" is updated successfully";
	}
	
	public String deleteCart(String cartId) {
		cartRepository.deleteById(cartId);
		return cartId +" is deleted successfully";
	}
}

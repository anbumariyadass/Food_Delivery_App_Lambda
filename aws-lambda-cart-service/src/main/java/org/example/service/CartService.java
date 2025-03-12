package org.example.service;

import org.example.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.repository.CartRepository;

@Service
public class CartService {
	@Autowired
    private CartRepository cartRepository;

	public Cart saveCart(Cart cart) {
		cartRepository.save(cart);
		return cart;
	}

	public Cart getCart(String userName) {
		return cartRepository.findByUserName(userName);
	}

	public void deleteCart(String userName) {
		cartRepository.deleteByUserName(userName);
	}
}

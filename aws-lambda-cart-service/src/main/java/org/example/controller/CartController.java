package org.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.example.entity.Cart;
import org.example.service.cartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@GetMapping
	public ResponseEntity<String> welcome() {
		return ResponseEntity.ok("Welcome to Cart Service....");
	}
	
	@Autowired
	private cartService cartService;
	
	@PostMapping
	public ResponseEntity<String> addCart(@RequestBody Cart cart) {
		String result = cartService.addCart(cart);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/{cartId}")
	public ResponseEntity<Cart> getCart(@PathVariable String cartId) {
		Cart cart = cartService.getCart(cartId);
		return ResponseEntity.ok(cart);
	}
	
	@PutMapping
	public ResponseEntity<String> updateCart(@RequestBody Cart cart) {
		String result = cartService.updateCart(cart);
		return ResponseEntity.ok(result);
	}
	
	@DeleteMapping("/{cartId}")
	public ResponseEntity<String> deleteCart(@PathVariable String cartId) {
		String response = cartService.deleteCart(cartId);
		return ResponseEntity.ok(response);
	}
}

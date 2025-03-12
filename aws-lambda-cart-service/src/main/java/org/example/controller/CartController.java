package org.example.controller;

import org.example.dto.ApiResponse;
import org.example.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.example.service.CartService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")  // Allows requests from any origin
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private CartService cartService;

	@GetMapping("/init")
	public ResponseEntity<ApiResponse> welcome() {
		return ResponseEntity.ok(new ApiResponse("Welcome to Cart Service....", null));
	}


	@PostMapping("/save")
	public ResponseEntity<ApiResponse> addCart(@RequestBody Cart cart) {
		Cart addedCart = cartService.saveCart(cart);
		return ResponseEntity.ok(new ApiResponse("Cart is saved successfully", addedCart));
	}

	@GetMapping("/{userName}")
	public ResponseEntity<ApiResponse> getCart(@PathVariable String userName) {
		Cart cart = cartService.getCart(userName);
		return ResponseEntity.ok(new ApiResponse("Cart is retrieved successfully", cart));
	}


	@DeleteMapping("/{userName}")
	public ResponseEntity<ApiResponse> deleteCart(@PathVariable String userName) {
		cartService.deleteCart(userName);
		return ResponseEntity.ok(new ApiResponse("Cart is deleted successfully", null));
	}
}

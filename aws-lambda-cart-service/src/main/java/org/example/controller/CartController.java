package org.example.controller;

import org.example.dto.ApiResponse;
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

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@GetMapping
	public ResponseEntity<ApiResponse> welcome() {
		return ResponseEntity.ok(new ApiResponse("Welcome to Cart Service....",null));
	}
	
	@Autowired
	private cartService cartService;
	
	@PostMapping
	public ResponseEntity<ApiResponse> addCart(@RequestBody Cart cart) {
		String result = cartService.addCart(cart);
		return ResponseEntity.ok(new ApiResponse(result,null));
	}
	
	@GetMapping("/{cartId}")
	public ResponseEntity<ApiResponse> getCart(@PathVariable String cartId) {
		Cart cart = cartService.getCart(cartId);
		return ResponseEntity.ok(new ApiResponse("Cart details retrieved successfully", cart));
	}
	
	@PutMapping
	public ResponseEntity<ApiResponse> updateCart(@RequestBody Cart cart) {
		String result = cartService.updateCart(cart);
		return ResponseEntity.ok(new ApiResponse(result, null));
	}
	
	@DeleteMapping("/{cartId}")
	public ResponseEntity<ApiResponse> deleteCart(@PathVariable String cartId) {
		String response = cartService.deleteCart(cartId);
		return ResponseEntity.ok(new ApiResponse(response, null));
	}

	@GetMapping("/allcarts/{customerUserName}")
	public ResponseEntity<ApiResponse> getAllCartByCustomerName(@PathVariable String customerUserName) {
		List<Cart> allCarts = cartService.getCartItemsForUser(customerUserName);
		return ResponseEntity.ok(new ApiResponse("All Cart details are retrieved successfully", allCarts));
	}

	@DeleteMapping("/allcarts/{customerUserName}")
	public ResponseEntity<ApiResponse> deleteAllCartByCustomerName(@PathVariable String customerUserName) {
		cartService.deleteCartItemsForUser(customerUserName);
		return ResponseEntity.ok(new ApiResponse("All Cart details are deleted successfully", null));
	}
}

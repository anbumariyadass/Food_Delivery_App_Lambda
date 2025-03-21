package com.iris.food_delivery.admin_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.iris.food_delivery.admin_service.service.SnsService;
import com.iris.food_delivery.admin_service.dto.ApiResponse;
import com.iris.food_delivery.admin_service.dto.Message;

@RestController
@CrossOrigin(origins = "*")  // Allows requests from any origin
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private SnsService snsService;
	
	@GetMapping("/ping")
	public ResponseEntity<ApiResponse> ping(){
		return ResponseEntity.ok(new ApiResponse("Hello from Admin service...", null));
	}
	
	@PostMapping("/notification/publishMessage")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<ApiResponse> publishMessage(@RequestBody Message message) {
		String response = snsService.publishMessage(message.getContent(), message.getSubject());
		return ResponseEntity.ok(new ApiResponse("SUCCESS", response));
	}
	
	@PostMapping("/notification/addSubscription/{mailId}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<ApiResponse> addSubscription(@PathVariable String mailId) {
		String response = snsService.subscribeEmail(mailId);
		return ResponseEntity.ok(new ApiResponse("SUCCESS", response));
	}
}

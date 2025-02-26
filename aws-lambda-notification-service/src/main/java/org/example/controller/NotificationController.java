package org.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.example.service.SnsService;

@RestController
@RequestMapping("/notification")
public class NotificationController {
	@Autowired
	private SnsService snsService;
	
	
	@PostMapping("/publishMessage")
	public ResponseEntity<String> publishMessage() {
		String response = snsService.publishMessage("Test content from notification service", "Message from Admin");
		return ResponseEntity.ok("Message is published successfully..." + response);
	}
	
	@PostMapping("/addSubscription/{mailId}")
	public ResponseEntity<String> addSubscription(@PathVariable String mailId) {
		String response = snsService.subscribeEmail(mailId);
		return ResponseEntity.ok("Subscriber is added successfully..." + response);
	}
}

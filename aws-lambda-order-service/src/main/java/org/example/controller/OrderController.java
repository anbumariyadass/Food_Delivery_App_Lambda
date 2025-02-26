package org.example.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.example.service.SqsService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private SqsService sqsService;
	
	@PostMapping
	public ResponseEntity<String> createOrder() {
		Random random = new Random();
        int randomThreeDigitNumber = 100 + random.nextInt(900); // Generates a number from 100 to 999
        String queueResponse = sqsService.sendMessage("Order Id::"+randomThreeDigitNumber);
		return ResponseEntity.ok("Order is created successfully. Order Id is " + randomThreeDigitNumber+". Queue Response : "+queueResponse);
	}
}

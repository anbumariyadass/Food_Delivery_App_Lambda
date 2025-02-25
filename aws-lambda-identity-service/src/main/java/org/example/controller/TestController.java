package org.example.controller;

import org.example.service.IdentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    IdentificationService identificationService;

    @GetMapping
    public ResponseEntity<String> getTestMessage() {
        String result = identificationService.welcome();
        return ResponseEntity.ok(result);
    }
}

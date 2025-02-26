package org.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {
    @PostMapping("/processorder")
    public ResponseEntity<String> checkConnection(@RequestBody String orderMessage) {
        return ResponseEntity.ok("Message returned from delivery service. " + orderMessage);
    }
}

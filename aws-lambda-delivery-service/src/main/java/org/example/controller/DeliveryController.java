package org.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {
    @GetMapping("/connect/{ordermsg}")
    public ResponseEntity<String> checkConnection(@PathVariable String ordermsg) {
        return ResponseEntity.ok("Message returned from delivery service. "+ordermsg);
    }
}

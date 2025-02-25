package org.example.service;

import org.springframework.stereotype.Service;

@Service
public class IdentificationService {
    public String welcome() {
        return "this message is coming from Indentification Service";
    }
}

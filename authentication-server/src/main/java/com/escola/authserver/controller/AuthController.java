package com.escola.authserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/health-check")
    public String healthCheck() {
        return "healthy";
    }

    @GetMapping("/token")
    public String token() {
        return "token";
    }
}

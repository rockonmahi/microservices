package com.escola.userservice.controller;

import com.escola.userservice.form.PaymentRequest;
import com.escola.userservice.form.PaymentResponse;
import com.escola.userservice.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public CompletableFuture<PaymentResponse> makePayment(@RequestBody PaymentRequest request) {
        return paymentService.processPayment(request);
    }
}

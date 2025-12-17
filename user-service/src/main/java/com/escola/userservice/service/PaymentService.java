package com.escola.userservice.service;

import com.escola.userservice.client.PaymentClient;
import com.escola.userservice.form.PaymentRequest;
import com.escola.userservice.form.PaymentResponse;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class PaymentService {

    private final PaymentClient paymentClient;

    public PaymentService(PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    @CircuitBreaker(name = "paymentService", fallbackMethod = "fallbackPayment")
    @Retry(name = "paymentService")
    @Bulkhead(name = "paymentService")
    @TimeLimiter(name = "paymentService")
    public CompletableFuture<PaymentResponse> processPayment(PaymentRequest request) {

        return CompletableFuture.supplyAsync(() ->
                paymentClient.charge(request)
        );
    }

    public CompletableFuture<PaymentResponse> fallbackPayment(
            PaymentRequest request, Throwable ex) {

        log.error("Payment failed, triggering fallback: {}", ex.getMessage());

        return CompletableFuture.completedFuture(
                new PaymentResponse("QUEUED", "Payment will be retried shortly")
        );
    }
}


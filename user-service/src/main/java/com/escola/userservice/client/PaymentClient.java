package com.escola.userservice.client;

import com.escola.userservice.form.PaymentRequest;
import com.escola.userservice.form.PaymentResponse;
import org.springframework.stereotype.Component;

@Component
public class PaymentClient {

    public PaymentResponse charge(PaymentRequest request) {
        if (Math.random() > 0.5) {
            throw new RuntimeException("Payment gateway timeout");
        }
        return new PaymentResponse("SUCCESS", "Payment processed");
    }
}


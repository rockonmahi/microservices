package com.escola.userservice.form;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentRequest {
    private String orderId;
    private double amount;

}


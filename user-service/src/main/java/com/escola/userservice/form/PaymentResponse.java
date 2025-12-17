package com.escola.userservice.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PaymentResponse {
    private String status;
    private String message;
}


package com.escola.authserver.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDetails {
    private final String firstName;
    private final String middleName;
    private final String lastName;
}

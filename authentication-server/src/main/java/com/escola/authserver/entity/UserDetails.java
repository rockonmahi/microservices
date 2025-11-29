package com.escola.authserver.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
public class UserDetails {
    private final String firstName;
    private final String middleName;
    private final String lastName;
}

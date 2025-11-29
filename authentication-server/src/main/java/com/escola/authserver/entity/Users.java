package com.escola.authserver.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Document(collection = "users")
public class Users {

    @Id
    private final String id;
    private final String userName;
    private final String password;
    private final String fullName;
    private final int active;
    private final int accountLock;

    private final UserDetails userDetails;
}

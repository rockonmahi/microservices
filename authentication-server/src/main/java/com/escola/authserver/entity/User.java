package com.escola.authserver.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class User {
    private final String id;
    private final String userName;
    private final String password;
    private final String fullName;
    private final int active;
    private final int accountLock;
}

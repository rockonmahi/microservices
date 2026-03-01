package com.escola.authserver.entity;

import java.time.Instant;
import java.util.Set;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "registered_clients")
@Data
@Builder
public class RegisteredClients {

    @Id
    private String id;

    @Indexed(unique = true)
    private String clientId;

    private Instant clientIdIssuedAt;

    private String clientSecret;

    private Instant clientSecretExpiresAt;

    private String clientName;

    private Set<String> clientAuthenticationMethods;
    private Set<String> authorizationGrantTypes;

    private Set<String> redirectUris;
    private Set<String> postLogoutRedirectUris;
    private Set<String> scopes;

    private boolean requireProofKey;
    private boolean requireAuthorizationConsent;

    private long accessTokenTimeToLive;
    private long refreshTokenTimeToLive;
    private boolean reuseRefreshTokens;
    private String accessTokenFormat;

    private Instant createdAt;
    private Instant updatedAt;
}
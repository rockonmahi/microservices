package com.escola.authserver.repository.impl;

import com.escola.authserver.entity.RegisteredClients;
import com.escola.authserver.repository.RegisteredClientsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RegisteredClientRepositoryImpl implements org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository {

    private final RegisteredClientsRepository registeredClientSecretRepository;

    @Override
    public void save(RegisteredClient registeredClient) {
        save(convert(registeredClient));
    }

    private RegisteredClients save(RegisteredClients registeredClient) {
       return registeredClientSecretRepository.save(registeredClient);
    }

    @Override
    public RegisteredClient findById(String id) {
        return convert(registeredClientSecretRepository.findById(id).orElseThrow());
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return convert(registeredClientSecretRepository.findByClientId(clientId).orElseGet(() -> registeredClientNotFound(clientId)));
    }

    private RegisteredClients registeredClientNotFound(String clientId) {
        RegisteredClient registeredClient;
        switch (clientId) {
            case "clientCredentialsSelfContained":
                registeredClient = RegisteredClient.withId("client-credentials-self-contained")
                        .clientId("clientCredentialsSelfContained")
                        .clientSecret("{bcrypt}$2a$10$MOvO4ycIuh3SaO0avHILm.yeTkB2PJ98DK3c0vQ3vkxZk9UrmLQXC")
                        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                        .scopes(scopes -> scopes.addAll(
                                List.of(OidcScopes.OPENID, OidcScopes.EMAIL, OidcScopes.PHONE)))
                        .tokenSettings(TokenSettings.builder()
                                .accessTokenTimeToLive(Duration.ofMinutes(10))
                                .accessTokenFormat(new OAuth2TokenFormat("self-contained"))
                                .build())
                        .build();
                break;
            case "clientCredentialsReference":
                registeredClient = RegisteredClient.withId("client-credentials-reference")
                        .clientId("clientCredentialsReference")
                        .clientSecret("{bcrypt}$2a$12$ahlGFAzG6zg1.IIGv6wLEOeWFS3E2IFKYZoe2ahsBmyY0OSAfTFaO")
                        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                        .scopes(scopes -> scopes.addAll(
                                List.of(OidcScopes.OPENID, OidcScopes.EMAIL, OidcScopes.PHONE)))
                        .tokenSettings(TokenSettings.builder()
                                .accessTokenTimeToLive(Duration.ofMinutes(10))
                                .accessTokenFormat(new OAuth2TokenFormat("reference"))
                                .build())
                        .build();
                break;
            case "authorizationCodeSelfContained":
                registeredClient = RegisteredClient.withId("authorization-code-self-contained")
                        .clientId("authorizationCodeSelfContained")
                        .clientSecret("{bcrypt}$2a$12$IBnNwjtJbW99CAnnMPtG6OcFSYPCTk6e/dXBiRfdgIVER1/rMFVCi")
                        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                        .redirectUri("https://oauth.pstmn.io/v1/callback")
                        .scopes(scopes -> scopes.addAll(
                                List.of(OidcScopes.OPENID, OidcScopes.EMAIL, OidcScopes.PHONE)))
                        .tokenSettings(TokenSettings.builder()
                                .accessTokenTimeToLive(Duration.ofMinutes(10))
                                .refreshTokenTimeToLive(Duration.ofHours(8))
                                .reuseRefreshTokens(false)
                                .accessTokenFormat(new OAuth2TokenFormat("self-contained"))
                                .build())
                        .build();
                break;
            case "eazypublicclient":
                registeredClient = RegisteredClient.withId("pkce-public-client")
                        .clientId("eazypublicclient")
                        .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                        .redirectUri("https://oauth.pstmn.io/v1/callback")
                        .scopes(scopes -> scopes.addAll(
                                List.of(OidcScopes.OPENID, OidcScopes.EMAIL, OidcScopes.PHONE)))
                        .clientSettings(ClientSettings.builder()
                                .requireProofKey(true)
                                .build())
                        .tokenSettings(TokenSettings.builder()
                                .accessTokenTimeToLive(Duration.ofMinutes(10))
                                .refreshTokenTimeToLive(Duration.ofHours(8))
                                .reuseRefreshTokens(false)
                                .accessTokenFormat(new OAuth2TokenFormat("self-contained"))
                                .build())
                        .build();
                break;
            case "passwordSelfContained":
                registeredClient = RegisteredClient.withId("password-self-contained")
                        .clientId("passwordSelfContained")
                        .clientSecret("{bcrypt}$2a$12$Q6de7Lt/AWs0OeL4bbnL7eDNldO8n5Rn6b8adTSD1TmtwtYQBOB1W")
                        .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                        .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                        .scopes(scopes -> scopes.addAll(
                                List.of(OidcScopes.OPENID, OidcScopes.EMAIL, OidcScopes.PHONE)))
                        .tokenSettings(TokenSettings.builder()
                                .accessTokenTimeToLive(Duration.ofMinutes(10))
                                .refreshTokenTimeToLive(Duration.ofHours(8))
                                .reuseRefreshTokens(false)
                                .accessTokenFormat(new OAuth2TokenFormat("self-contained"))
                                .build())
                        .build();
                break;
            default:
                throw new IllegalArgumentException("Unknown clientId: " + clientId);
        }

        return save(convert(registeredClient));
    }

    public RegisteredClient convert(RegisteredClients source) {

        RegisteredClient.Builder builder =
                RegisteredClient.withId(source.getId())
                        .clientId(source.getClientId())
                        .clientName(source.getClientName());

        if (source.getClientIdIssuedAt() != null) {
            builder.clientIdIssuedAt(source.getClientIdIssuedAt());
        }

        if (source.getClientSecret() != null) {
            builder.clientSecret(source.getClientSecret());
        }

        if (source.getClientSecretExpiresAt() != null) {
            builder.clientSecretExpiresAt(source.getClientSecretExpiresAt());
        }

        source.getClientAuthenticationMethods()
                .forEach(v ->
                        builder.clientAuthenticationMethod(
                                new ClientAuthenticationMethod(v)));

        source.getAuthorizationGrantTypes()
                .forEach(v ->
                        builder.authorizationGrantType(
                                new AuthorizationGrantType(v)));

        builder.redirectUris(uris ->
                uris.addAll(source.getRedirectUris()));

        builder.postLogoutRedirectUris(uris ->
                uris.addAll(source.getPostLogoutRedirectUris()));

        builder.scopes(scopes ->
                scopes.addAll(source.getScopes()));

        builder.clientSettings(
                ClientSettings.builder()
                        .requireProofKey(source.isRequireProofKey())
                        .requireAuthorizationConsent(source.isRequireAuthorizationConsent())
                        .build()
        );

        TokenSettings.Builder tokenBuilder = TokenSettings.builder()
                .accessTokenTimeToLive(
                        Duration.ofSeconds(source.getAccessTokenTimeToLive()))
                .reuseRefreshTokens(source.isReuseRefreshTokens())
                .accessTokenFormat(
                        new OAuth2TokenFormat(source.getAccessTokenFormat()));

        if (source.getRefreshTokenTimeToLive() > 0) {
            tokenBuilder.refreshTokenTimeToLive(
                    Duration.ofSeconds(source.getRefreshTokenTimeToLive()));
        }

        builder.tokenSettings(tokenBuilder.build());

        return builder.build();
    }

    public RegisteredClients convert(RegisteredClient source) {

        return RegisteredClients.builder()
                .id(source.getId())
                .clientId(source.getClientId())
                .clientIdIssuedAt(source.getClientIdIssuedAt())
                .clientSecret(source.getClientSecret())
                .clientSecretExpiresAt(source.getClientSecretExpiresAt())
                .clientName(source.getClientName())

                .clientAuthenticationMethods(
                        source.getClientAuthenticationMethods()
                                .stream()
                                .map(ClientAuthenticationMethod::getValue)
                                .collect(Collectors.toSet())
                )

                .authorizationGrantTypes(
                        source.getAuthorizationGrantTypes()
                                .stream()
                                .map(AuthorizationGrantType::getValue)
                                .collect(Collectors.toSet())
                )

                .redirectUris(source.getRedirectUris())
                .postLogoutRedirectUris(source.getPostLogoutRedirectUris())
                .scopes(source.getScopes())

                .requireProofKey(
                        source.getClientSettings().isRequireProofKey())
                .requireAuthorizationConsent(
                        source.getClientSettings().isRequireAuthorizationConsent())

                .accessTokenTimeToLive(
                        source.getTokenSettings()
                                .getAccessTokenTimeToLive()
                                .getSeconds())

                .refreshTokenTimeToLive(
                        source.getTokenSettings().getRefreshTokenTimeToLive() != null
                                ? source.getTokenSettings()
                                .getRefreshTokenTimeToLive()
                                .getSeconds()
                                : 0)

                .reuseRefreshTokens(
                        source.getTokenSettings().isReuseRefreshTokens())

                .accessTokenFormat(
                        source.getTokenSettings()
                                .getAccessTokenFormat()
                                .getValue())

                .updatedAt(Instant.now())
                .build();
    }
}

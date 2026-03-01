package com.escola.authserver.repository;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OAuth2RegisteredClientRepository implements org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository {

    @Override
    public void save(RegisteredClient registeredClient) {
        this.save(registeredClient);
    }

    @Override
    public RegisteredClient findById(String id) {
      return this.findById(id);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
       return saveRegisteredClient(clientId);
    }

    private RegisteredClient saveRegisteredClient(String clientId) {
        Map<String,RegisteredClient> map = new HashMap<>();

        RegisteredClient clientCredentialsSelfContained = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("clientCredentialsSelfContained")
                .clientSecret("{bcrypt}$2a$10$MOvO4ycIuh3SaO0avHILm.yeTkB2PJ98DK3c0vQ3vkxZk9UrmLQXC")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scopes(scopeConfig -> scopeConfig.addAll(List.of(OidcScopes.OPENID, OidcScopes.EMAIL, OidcScopes.PHONE)))
                .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofMinutes(10))
                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED).build()).build();

        RegisteredClient clientCredentialsReference = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("clientCredentialsReference")
                .clientSecret("{noop}c1BK9Bg2REeydBbvUoUeKCbD2bvJzXGj")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scopes(scopeConfig -> scopeConfig.addAll(List.of(OidcScopes.OPENID, OidcScopes.EMAIL, OidcScopes.PHONE)))
                .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofMinutes(10))
                        .accessTokenFormat(OAuth2TokenFormat.REFERENCE).build()).build();

        RegisteredClient authorizationCodeSelfContained = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("authorizationCodeSelfContained")
                .clientSecret("{noop}Qw3rTy6UjMnB9zXcV2pL0sKjHn5TxQqB")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("https://oauth.pstmn.io/v1/callback")
                .scopes(scopeConfig -> scopeConfig.addAll(List.of(OidcScopes.OPENID, OidcScopes.EMAIL, OidcScopes.PHONE)))
                .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofMinutes(10))
                        .refreshTokenTimeToLive(Duration.ofHours(8)).reuseRefreshTokens(false)
                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED).build()).build();

        RegisteredClient pkceClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("eazypublicclient")
                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("https://oauth.pstmn.io/v1/callback")
                .scopes(scopeConfig -> scopeConfig.addAll(List.of(OidcScopes.OPENID, OidcScopes.EMAIL, OidcScopes.PHONE)))
                .clientSettings(ClientSettings.builder().requireProofKey(true).build())
                .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofMinutes(10))
                        .refreshTokenTimeToLive(Duration.ofHours(8)).reuseRefreshTokens(false)
                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED).build()).build();

        RegisteredClient passwordSelfContained = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("passwordSelfContained")
                .clientSecret("{noop}password123")
                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                .scopes(scopeConfig -> scopeConfig.addAll(List.of(OidcScopes.OPENID, OidcScopes.EMAIL, OidcScopes.PHONE)))
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofMinutes(10))
                        .refreshTokenTimeToLive(Duration.ofHours(8))
                        .reuseRefreshTokens(false)
                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                        .build())
                .build();

        map.put("clientCredentialsSelfContained", clientCredentialsSelfContained);
        map.put("clientCredentialsReference", clientCredentialsReference);
        map.put("authorizationCodeSelfContained", authorizationCodeSelfContained);
        map.put("eazypublicclient", pkceClient);
        map.put("passwordSelfContained", passwordSelfContained);

        return map.get(clientId);
    }
}

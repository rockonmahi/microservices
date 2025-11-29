package com.escola.saml2.bean;

import com.escola.saml2.filter.Saml2Filter;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.core.AttributeStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.saml2.provider.service.authentication.OpenSaml4AuthenticationProvider;
import org.springframework.security.saml2.provider.service.authentication.Saml2Authentication;
import org.springframework.security.saml2.provider.service.metadata.OpenSamlMetadataResolver;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.web.DefaultRelyingPartyRegistrationResolver;
import org.springframework.security.saml2.provider.service.web.RelyingPartyRegistrationResolver;

import java.util.Collection;
import java.util.List;

@Configuration
public class Saml2Bean {

    @Autowired
    RelyingPartyRegistrationRepository relyingPartyRegistrationRepository;

    @Bean
    public Saml2Filter saml2Filter() {
        Saml2Filter filter = new Saml2Filter(
                (RelyingPartyRegistrationResolver) new DefaultRelyingPartyRegistrationResolver(this.relyingPartyRegistrationRepository),
                new OpenSamlMetadataResolver());
        return filter;
    }

    @Bean
    public OpenSaml4AuthenticationProvider samlAuthProv() {

        Converter<OpenSaml4AuthenticationProvider.ResponseToken, Saml2Authentication> authConvertor
                = OpenSaml4AuthenticationProvider.createDefaultResponseAuthenticationConverter();

        OpenSaml4AuthenticationProvider samlAuthProv = new OpenSaml4AuthenticationProvider();

        samlAuthProv.setResponseAuthenticationConverter(responseToken -> {
            Saml2Authentication authentication = authConvertor.convert(responseToken);

            Assertion assertion = responseToken.getResponse().getAssertions().get(0);
            AuthenticatedPrincipal principal = (AuthenticatedPrincipal) authentication.getPrincipal();

            System.out.println(principal.getName());
            System.out.println(authentication.getPrincipal().toString());
            System.out.println(assertion.toString());

            Collection<? extends GrantedAuthority> authorities = authoritiesExtractor().convert(assertion);
            return new Saml2Authentication(principal, authentication.getSaml2Response(), authorities);
        });

        return samlAuthProv;
    }


    private static Converter<Assertion, Collection<? extends GrantedAuthority>> authoritiesExtractor() {
        Converter<Assertion, Collection<? extends GrantedAuthority>> authoritiesExtractor = assertion -> {

            List<SimpleGrantedAuthority> userRoles
                    = assertion.getAttributeStatements().stream()
                    .map(AttributeStatement::getAttributes)
                    .flatMap(Collection::stream)
                    .filter(attr -> "groups".equalsIgnoreCase(attr.getName()))
                    .map(Attribute::getAttributeValues)
                    .flatMap(Collection::stream)
                    .map(xml -> new SimpleGrantedAuthority(xml.getDOM().getTextContent()))
                    .toList();
            return userRoles;
        };
        return authoritiesExtractor;
    }
}

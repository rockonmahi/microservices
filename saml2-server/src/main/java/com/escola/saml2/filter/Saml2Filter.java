package com.escola.saml2.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.security.saml2.Saml2Exception;
import org.springframework.security.saml2.provider.service.metadata.Saml2MetadataResolver;
import org.springframework.security.saml2.provider.service.metadata.Saml2MetadataResponse;
import org.springframework.security.saml2.provider.service.metadata.Saml2MetadataResponseResolver;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.web.DefaultRelyingPartyRegistrationResolver;
import org.springframework.security.saml2.provider.service.web.RelyingPartyRegistrationResolver;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

public class Saml2Filter extends OncePerRequestFilter {
    
    public static final String DEFAULT_METADATA_FILE_NAME = "saml-{registrationId}-metadata.xml";
    private final Saml2MetadataResponseResolver metadataResolver;

    public Saml2Filter(RelyingPartyRegistrationResolver relyingPartyRegistrationResolver, Saml2MetadataResolver saml2MetadataResolver) {
        Assert.notNull(relyingPartyRegistrationResolver, "relyingPartyRegistrationResolver cannot be null");
        Assert.notNull(saml2MetadataResolver, "saml2MetadataResolver cannot be null");
        this.metadataResolver = new Saml2Filter.Saml2MetadataResponseResolverAdapter(relyingPartyRegistrationResolver, saml2MetadataResolver);
    }

    public Saml2Filter(RelyingPartyRegistrationRepository relyingPartyRegistrationRepository, Saml2MetadataResolver saml2MetadataResolver) {
        this((RelyingPartyRegistrationResolver)(new DefaultRelyingPartyRegistrationResolver(relyingPartyRegistrationRepository)), saml2MetadataResolver);
    }

    public Saml2Filter(Saml2MetadataResponseResolver metadataResponseResolver) {
        this.metadataResolver = metadataResponseResolver;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        Saml2MetadataResponse metadata;
        try {
            metadata = this.metadataResolver.resolve(request);
        } catch (Saml2Exception var6) {
            response.setStatus(401);
            return;
        }

        if (metadata == null) {
            chain.doFilter(request, response);
        } else {
            this.writeMetadataToResponse(response, metadata);
        }
    }

    private void writeMetadataToResponse(HttpServletResponse response, Saml2MetadataResponse metadata) throws IOException {
        response.setContentType("application/xml");
        String format = "attachment; filename=\"%s\"; filename*=UTF-8''%s";
        String fileName = metadata.getFileName();
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition", String.format(format, fileName, encodedFileName));
        response.setContentLength(metadata.getMetadata().getBytes(StandardCharsets.UTF_8).length);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(metadata.getMetadata());
    }

    public void setRequestMatcher(RequestMatcher requestMatcher) {
        Assert.notNull(requestMatcher, "requestMatcher cannot be null");
        Assert.isInstanceOf(Saml2Filter.Saml2MetadataResponseResolverAdapter.class, this.metadataResolver, "a Saml2MetadataResponseResolver and RequestMatcher cannot be both set on this filter. Please set the request matcher on the Saml2MetadataResponseResolver itself.");
        ((Saml2Filter.Saml2MetadataResponseResolverAdapter)this.metadataResolver).setRequestMatcher(requestMatcher);
    }

    public void setMetadataFilename(String metadataFilename) {
        Assert.hasText(metadataFilename, "metadataFilename cannot be empty");
        Assert.isTrue(metadataFilename.contains("{registrationId}"), "metadataFilename must contain a {registrationId} match variable");
        Assert.isInstanceOf(Saml2Filter.Saml2MetadataResponseResolverAdapter.class, this.metadataResolver, "a Saml2MetadataResponseResolver and file name cannot be both set on this filter. Please set the file name on the Saml2MetadataResponseResolver itself.");
        ((Saml2Filter.Saml2MetadataResponseResolverAdapter)this.metadataResolver).setMetadataFilename(metadataFilename);
    }

    private static final class Saml2MetadataResponseResolverAdapter implements Saml2MetadataResponseResolver {
        private final RelyingPartyRegistrationResolver registrations;
        private RequestMatcher requestMatcher = new AntPathRequestMatcher("/saml2/service-provider-metadata/{registrationId}");
        private final Saml2MetadataResolver metadataResolver;
        private String metadataFilename = "saml-{registrationId}-metadata.xml";

        Saml2MetadataResponseResolverAdapter(RelyingPartyRegistrationResolver registrations, Saml2MetadataResolver metadataResolver) {
            this.registrations = registrations;
            this.metadataResolver = metadataResolver;
        }

        public Saml2MetadataResponse resolve(HttpServletRequest request) {
            RequestMatcher.MatchResult matcher = this.requestMatcher.matcher(request);
            if (!matcher.isMatch()) {
                return null;
            } else {
                String registrationId = (String)matcher.getVariables().get("registrationId");
                RelyingPartyRegistration relyingPartyRegistration = this.registrations.resolve(request, registrationId);
                if (relyingPartyRegistration == null) {
                    throw new Saml2Exception("registration not found");
                } else {
                    registrationId = relyingPartyRegistration.getRegistrationId();
                    String metadata = this.metadataResolver.resolve(relyingPartyRegistration);
                    String fileName = this.metadataFilename.replace("{registrationId}", registrationId);
                    return new Saml2MetadataResponse(metadata, fileName);
                }
            }
        }

        void setRequestMatcher(RequestMatcher requestMatcher) {
            Assert.notNull(requestMatcher, "requestMatcher cannot be null");
            this.requestMatcher = requestMatcher;
        }

        void setMetadataFilename(String metadataFilename) {
            Assert.hasText(metadataFilename, "metadataFilename cannot be empty");
            Assert.isTrue(metadataFilename.contains("{registrationId}"), "metadataFilename must contain a {registrationId} match variable");
            this.metadataFilename = metadataFilename;
        }
    }
}
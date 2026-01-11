FROM amazoncorretto:17-alpine-jdk
COPY target/saml2-server-1.jar saml2-server.jar
ENTRYPOINT ["java","-jar","/saml2-server.jar","--spring.profiles.active=cloud"]
FROM amazoncorretto:17-alpine-jdk
COPY target/api-gateway-1.jar  api-gateway.jar
ENTRYPOINT ["java","-jar","/api-gateway.jar","--spring.profiles.active=cloud"]
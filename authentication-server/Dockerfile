FROM amazoncorretto:17-alpine-jdk
COPY target/authentication-server-1.jar authentication-server.jar
ENTRYPOINT ["java","-jar","/authentication-server.jar","--spring.profiles.active=cloud"]
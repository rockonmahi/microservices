FROM amazoncorretto:17-alpine-jdk
COPY target/user-service-1.jar  user-service.jar
ENTRYPOINT ["java","-jar","/user-service.jar","--spring.profiles.active=cloud"]
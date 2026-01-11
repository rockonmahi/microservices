FROM amazoncorretto:17-alpine-jdk
COPY target/registry-service-1.jar registry-service.jar
ENTRYPOINT ["java","-jar","/registry-service.jar","--spring.profiles.active=cloud"]
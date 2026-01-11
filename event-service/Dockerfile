FROM amazoncorretto:17-alpine-jdk
COPY target/event-service-1.jar  event-service.jar
ENTRYPOINT ["java","-jar","/event-service.jar","--spring.profiles.active=cloud"]
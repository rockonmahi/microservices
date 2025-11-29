FROM amazoncorretto:17-alpine-jdk
COPY target/resource-server-1.jar  resource-server.jar
ENTRYPOINT ["java","-jar","/resource-server.jar"]
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/gateway-Service-0.0.1-SNAPSHOT.jar gateway-Service.jar

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "gateway-Service.jar"]
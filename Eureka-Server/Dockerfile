FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/Eureka-Server-0.0.1-SNAPSHOT.jar Eureka-Server.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "Eureka-Server.jar"]
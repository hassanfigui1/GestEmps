FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/performance-evaluation-Service-0.0.1-SNAPSHOT.jar performance-evaluation-Service

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "performance-evaluation-Service.jar"]
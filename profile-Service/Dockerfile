FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/profile-Service-0.0.1-SNAPSHOT.jar profile-Service.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "profile-Service.jar"]
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/leave-mgt-Service-0.0.1-SNAPSHOT.jar leave-mgt-Service.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "leave-mgt-Service.jar"]
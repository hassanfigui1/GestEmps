FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/payroll-Service-0.0.1-SNAPSHOT.jar payroll-Service.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "leave-mgt-Service.jar"]
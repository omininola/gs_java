FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . .

RUN ./mvnw clean package

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/api_gs-0.0.1-SNAPSHOT.jar"]
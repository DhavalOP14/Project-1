# Stage 1: Build the Spring Boot application using Maven and JDK 21
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run with lightweight Alpine JRE to conserve RAM on free hosting tiers
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Xmx400m", "-jar", "app.jar"]

# Stage 1: Build the application using Maven
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the application JAR, skipping tests
RUN mvn clean package -DskipTests

# Stage 2: Lightweight runtime image
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy the generated JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose standard Spring Boot port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]

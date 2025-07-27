# Use OpenJDK 21 as base image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory inside the container
WORKDIR /app

# Copy the compiled Spring Boot JAR file into the container
COPY target/the-moving-company-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 to allow incoming traffic
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

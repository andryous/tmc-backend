# Use OpenJDK 21 with Maven installed
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

# Set working directory inside the container
WORKDIR /app

# Copy pom.xml and download dependencies (to leverage Docker layer caching)
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY . .

# Package the application (this will create the .jar)
RUN mvn clean package -DskipTests

# ---- Production stage ----
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the built jar from the previous stage
COPY --from=build /app/target/the-moving-company-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8088

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

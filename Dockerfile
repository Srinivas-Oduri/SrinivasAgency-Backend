# Use OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory in container
WORKDIR /app

# Copy built jar file to container
COPY target/*.jar app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]

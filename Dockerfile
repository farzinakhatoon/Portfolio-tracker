# Use an official Maven image that includes Java and Maven
FROM maven:3.9.5-eclipse-temurin-21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the entire project and build it
COPY . .
RUN mvn clen package

# Use a lightweight base image for running the application
FROM eclipse-temurin:21-jre

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/portfolio-tracker-1.0.0.jar app.jar

# Expose the port your application runs on (e.g., 8080)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]

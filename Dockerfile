
# Use an official Maven image that includes Java and Maven
FROM maven:3.9.5-eclipse-temurin-21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file and download dependencies
COPY pom.xml . 
RUN mvn dependency:go-offline

# Copy the entire project and build it
COPY . .
RUN mvn clean package -DskipTests

# Use a lightweight base image for running the application
FROM eclipse-temurin:21-jre

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/portfolio-tracker-1.0.0.jar app.jar

# Copy the SSL CA certificate for the Aiven MySQL connection (ensure you have the `ca.pem` file)
COPY src/main/resources/ca.pem /app/ca.pem

# Expose the port your application runs on (e.g., 8080)
EXPOSE 8080


# Run the application
CMD ["java", "-Djavax.net.ssl.trustStore=/app/ca.pem", "-jar", "app.jar"]

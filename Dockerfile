FROM openjdk:17-jdk-alpine

WORKDIR /app

# Copy Maven Wrapper and configuration
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Copy source files
COPY src ./src

# Make the mvnw script executable
RUN chmod +x ./mvnw

# Use the Maven Wrapper for the package command
RUN ./mvnw clean package spring-boot:repackage

# Copy the JAR file to the /app directory
COPY target/library-manager-core-0.0.1-SNAPSHOT.jar /app/library-manager-core-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/library-manager-core-0.0.1-SNAPSHOT.jar"]

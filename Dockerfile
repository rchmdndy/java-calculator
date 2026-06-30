# Sample 2: Java Calculator dengan Docker
# Multi-stage build untuk production-ready image

# Stage 1: Build
FROM gradle:7.6-jdk11 AS builder

WORKDIR /app

# Copy build files & source
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY gradlew ./
COPY src ./src

# Build JAR
RUN chmod +x gradlew && ./gradlew build --no-daemon

# Stage 2: Runtime
FROM eclipse-temurin:11-jre

WORKDIR /app

# Copy JAR dari stage build
COPY --from=builder /app/build/libs/calculator-0.0.1-SNAPSHOT.jar app.jar

# Create non-root user
RUN useradd -m -s /bin/bash appuser && chown -R appuser:appuser /app
USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

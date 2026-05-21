# =========================
# Build stage
# =========================
FROM eclipse-temurin:25-jdk AS builder

WORKDIR /app

# Copy only necessary files to resolve dependencies first
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x mvnw
RUN ./mvnw -B -q dependency:go-offline

# Copy the rest of the code
COPY src src

# Build the artifact
RUN ./mvnw -B -q clean package -DskipTests


# =========================
# Runtime stage
# =========================
FROM eclipse-temurin:25-jre

WORKDIR /app

# Copy only the final jar
COPY --from=builder /app/target/*.jar app.jar

# Default application port
EXPOSE 8080

# Explicit profile configuration
ENV SPRING_PROFILES_ACTIVE=docker

ENTRYPOINT ["java", "-jar", "app.jar"]

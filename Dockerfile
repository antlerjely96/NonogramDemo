# Stage 1: Build file JAR với Java 26
FROM maven:3.9.6-eclipse-temurin-26 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Chạy ứng dụng với JRE 26
FROM eclipse-temurin:26-jre-alpine
WORKDIR /app
# Copy file JAR từ Stage 1 sang
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
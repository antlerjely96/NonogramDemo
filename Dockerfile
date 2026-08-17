# Stage 1: Dùng JDK 26 và cài đặt Maven trực tiếp
FROM eclipse-temurin:26-jdk AS build
WORKDIR /app

# Cài đặt Maven chuẩn từ kho ứng dụng của Linux (bỏ qua mvnw)
RUN apt-get update && apt-get install -y maven

# Chỉ copy pom.xml và thư mục src (không cần quan tâm mvnw hay .mvn nữa)
COPY pom.xml .
COPY src ./src

# Giới hạn RAM khi build
ENV MAVEN_OPTS="-Xmx256m"

# Build project bằng lệnh mvn gốc của hệ thống
RUN mvn clean package -DskipTests

# Stage 2: Chạy ứng dụng với JRE 26
FROM eclipse-temurin:26-jre
WORKDIR /app

# Copy file JAR từ Stage 1 sang
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
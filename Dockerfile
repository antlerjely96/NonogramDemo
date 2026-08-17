# Stage 1: Build bằng JDK 26 và Maven Wrapper có sẵn của dự án
FROM eclipse-temurin:26-jdk AS build
WORKDIR /app

# Copy toàn bộ code (bao gồm cả thư mục .mvn và file mvnw) vào Docker
COPY . .

# Cấp quyền thực thi cho file lệnh mvnw
RUN chmod +x ./mvnw

# Giới hạn RAM khi build
ENV MAVEN_OPTS="-Xmx256m"

# Dùng mvnw để build thay vì dùng maven cài sẵn
RUN ./mvnw clean package -DskipTests

# Stage 2: Chạy ứng dụng với JRE 26
FROM eclipse-temurin:26-jre
WORKDIR /app

# Copy file JAR từ Stage 1 sang
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
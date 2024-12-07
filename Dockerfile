FROM openjdk:17-jdk-slim
WORKDIR /app
COPY app.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]
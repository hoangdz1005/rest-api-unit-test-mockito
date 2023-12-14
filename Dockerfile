FROM openjdk:17
LABEL authors="hoangdz1005"
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]

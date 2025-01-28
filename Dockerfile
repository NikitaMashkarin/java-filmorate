
FROM amazoncorretto:21

WORKDIR /app

COPY target/*.jar app.jar
COPY src/main/resources/application.properties application.properties

ENTRYPOINT ["java", "-jar", "app.jar"]

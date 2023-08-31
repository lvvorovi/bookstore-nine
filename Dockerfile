FROM openjdk:17-ea-16-jdk
ARG JAR_FILE=build/libs/*jar
COPY ./build/libs/nine-2.0.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]


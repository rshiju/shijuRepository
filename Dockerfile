#FROM openjdk:11-jdk-alpine
FROM adoptopenjdk/openjdk11:latest
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} trainingPOC.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/trainingPOC.jar"]
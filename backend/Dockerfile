FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} english-training.jar
ENTRYPOINT ["java","-jar","/english-training.jar"]
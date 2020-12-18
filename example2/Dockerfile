FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} jira-cloud-plugin-2.0.jar
ENTRYPOINT ["java","-jar","/jira-cloud-plugin-2.0.jar"]

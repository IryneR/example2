FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} jira-cloud-plugin-2.0.jar
ENTRYPOINT ["java","-jar","/jira-cloud-plugin-2.0.jar"]



#FROM python:3.7-alpine
#WORKDIR D:/Projects/jiraplugin-service/src/api
#ENV FLASK_APP=api.py
#ENV FLASK_RUN_HOST=0.0.0.0
#RUN apk add --no-cache gcc musl-dev linux-headers
#COPY requirements.txt requirements.txt
#RUN pip install -r requirements.txt
#EXPOSE 5000
#COPY . .
#CMD ["flask", "run"]


FROM openjdk:8-jdk-alpine
ARG MYSQL_USER
ARG MYSQL_PASSWORD
ENV APP_HOME=/opt/phonebook
RUN mkdir $APP_HOME
COPY build/libs/*.jar $APP_HOME/
WORKDIR $APP_HOME

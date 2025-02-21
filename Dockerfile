## Use Eclipse Temurin JDK 23
#FROM eclipse-temurin:23-jdk-jammy
#
## Information about who maintains the image
#LABEL maintainer="nandakishore1234593@gmail.com"
#
## Add a volume pointing to /tmp
#VOLUME /tmp
#
## Make port 8080 available to the world outside this container
#EXPOSE 8080
#
## Set application's JAR file
#ARG JAR_FILE=target/*.jar
#
## Add the application's JAR file to the container
#COPY ${JAR_FILE} app.jar
#
## Run the jar file
#ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
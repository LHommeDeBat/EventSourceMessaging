FROM openjdk:11
ARG JAR_FILE=target/EventSourceMessaging-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} event-source-messaging.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=docker","-jar","/event-source-messaging.jar"]

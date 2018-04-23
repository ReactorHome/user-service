FROM openjdk:8-jre
MAINTAINER Reactor <reactor@myreactorhome.com>

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/user-service/user-service.jar"]

RUN mkdir -p /usr/share/user-service/face && chmod /usr/share/user-service/face 777

# Add the service itself
ARG JAR_FILE
ADD ./target/${JAR_FILE} /usr/share/user-service/user-service.jar
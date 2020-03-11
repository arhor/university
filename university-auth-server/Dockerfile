FROM openjdk:11

MAINTAINER Maksim Buryshynets <arhor18@gmail.com>

ADD ./target/university-auth-server.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/university-auth-server.jar"]

EXPOSE 5000
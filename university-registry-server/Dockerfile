FROM openjdk:11

MAINTAINER Maksim Buryshynets <arhor18@gmail.com>

ADD ./target/university-registry-server.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/university-registry-server.jar"]

EXPOSE 8761
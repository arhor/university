FROM openjdk:11

MAINTAINER Maksim Buryshynets <arhor18@gmail.com>

ADD ./target/university-config-server.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/university-config-server.jar"]

HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://localhost:8888/actuator/health || exit 1

EXPOSE 8888
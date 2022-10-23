FROM openjdk:11

COPY build/libs/SlowDelivery-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["nohup", "java", "-jar", "-Djava.security.egd=file:/dev/./urandom", "/app.jar"]
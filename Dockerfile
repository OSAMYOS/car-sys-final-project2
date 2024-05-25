FROM openjdk:21

COPY target/car-sys-final-project-0.0.1-SNAPSHOT.jar F.jar

CMD ["java", "-jar", "F.jar"]
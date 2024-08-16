FROM openjdk:17

WORKDIR /controlador

COPY target/controlador-0.0.1-SNAPSHOT.jar /controlador/controlador.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/controlador/controlador.jar"]
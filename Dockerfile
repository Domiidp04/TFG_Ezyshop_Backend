FROM openjdk:17-jdk
COPY target/TFG_Ezyshop_Backend-1.0.0.jar TFG_Ezyshop_Backend.jar
ENTRYPOINT ["java", "-jar","TFG_Ezyshop_Backend.jar"]
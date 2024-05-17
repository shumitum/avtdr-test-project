FROM amazoncorretto:21-alpine-jdk
COPY target/*.jar avtdr_app.jar
ENTRYPOINT ["java","-jar","/avtdr_app.jar"]
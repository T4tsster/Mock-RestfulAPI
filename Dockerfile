FROM amazoncorretto:11-alpine-jdk
MAINTAINER nhan.pham.r6
COPY target/contacts-api-1.0.jar mock-rest-api.jar
ENTRYPOINT ["java","-jar","/mock-restful-api.jar"]
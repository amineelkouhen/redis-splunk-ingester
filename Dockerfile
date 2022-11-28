# Stage 1 - Build
FROM maven:3.8-openjdk-18-slim AS build
MAINTAINER Amine El Kouhen
RUN mkdir /home/build
COPY . /home/build
WORKDIR /home/build
RUN ./home/build/gradlew build
COPY /home/build/libs/redis-splunk-ingester-1.0.0.jar redis-splunk-ingester-1.0.0.jar
ENTRYPOINT ["java","-jar","/redis-splunk-ingester-1.0.0.jar"]

## Stage 2 - Package
FROM --platform=linux/x86_64 openjdk:18-jdk-alpine AS runtime
COPY --from=build build/redis-splunk-ingester-1.0.0.jar app.jar
EXPOSE 8686
ENTRYPOINT ["java","-jar","/app.jar"]
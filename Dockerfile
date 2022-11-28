# Stage 1 - Build
FROM maven:3.8-openjdk-18-slim AS build
MAINTAINER Amine El Kouhen
WORKDIR /build
COPY gradlew .
COPY settings.gradle .
COPY gradle gradle
COPY src src
RUN ./gradlew build
COPY build/libs/redis-splunk-ingester-1.0.0.jar redis-splunk-ingester-1.0.0.jar
ENTRYPOINT ["java","-jar","/redis-splunk-ingester-1.0.0.jar"]

## Stage 2 - Package
FROM --platform=linux/x86_64 openjdk:18-jdk-alpine AS runtime
RUN gradle build --no-daemon
COPY --from=build build/redis-splunk-ingester-1.0.0.jar app.jar
EXPOSE 8686
ENTRYPOINT ["java","-jar","/app.jar"]
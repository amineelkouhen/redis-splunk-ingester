# Stage 1 - Build
FROM openjdk:18-ea-20-jdk-slim AS build
MAINTAINER Amine El Kouhen
RUN mkdir /ingester
COPY . /ingester
WORKDIR /ingester
RUN ./gradlew build --no-daemon
ENTRYPOINT ["java","-jar","/build/libs/redis-splunk-ingester-1.0.0.jar"]

## Stage 2 - Package
FROM --platform=linux/x86_64 openjdk:18-jdk-alpine AS runtime
COPY --from=build /ingester/build/libs/redis-splunk-ingester-1.0.0.jar app.jar
EXPOSE 8686
ENTRYPOINT ["java","-jar","/app.jar"]
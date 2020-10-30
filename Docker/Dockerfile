FROM alpine:3.11 AS builder
RUN apk update && \
    apk add 'nodejs>10.19.0-r0'
RUN apk add 'maven>3.6.1-r0'
RUN apk add openjdk11
WORKDIR /app
COPY pom.xml .
COPY package.json .
COPY package-lock.json .
COPY src ./src/
COPY frontend ./frontend/
RUN mvn package -Pproduction

FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /app
COPY --from=builder /app/target/ur-codebin-1.0-SNAPSHOT.jar app.jar
CMD java -jar -Dspring.profiles.active=prod app.jar

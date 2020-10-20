FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /app
COPY ../target/ur-codebin-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD java -jar -Dspring.profiles.active=prod /app/app.jar
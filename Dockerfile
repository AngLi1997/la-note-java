FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY liang-note-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app.jar"]

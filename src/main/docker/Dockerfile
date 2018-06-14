FROM java:8
VOLUME /tmp
ADD spring-study-0.0.1.*.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Dspring.profiles.active=development","-jar","/app.jar"]
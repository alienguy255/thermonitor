FROM eclipse-temurin:17-jdk

COPY ./target/monitor-1.0.jar /

CMD ["java", "-jar", "/monitor-1.0.jar"]
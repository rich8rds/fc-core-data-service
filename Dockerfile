FROM eclipse-temurin:23-jre

ADD target/UserService.jar /UserService.jar
ADD docker/collector/opentelemetry-javaagent.jar /opentelemetry-javaagent.jar

ENTRYPOINT java -javaagent:/opentelemetry-javaagent.jar -jar /UserService.jar
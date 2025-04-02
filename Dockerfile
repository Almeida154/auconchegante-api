FROM eclipse-temurin:21-jdk-alpine

WORKDIR /workspace/app

RUN apk add --no-cache maven

EXPOSE 8080
EXPOSE 5005

CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.jvmArguments='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005'"]
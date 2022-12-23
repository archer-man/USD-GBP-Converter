FROM maven:3.6.3-openjdk-11 as builder
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app

RUN mvn -f /usr/src/app/pom.xml clean package
FROM openjdk:11.0-jdk-slim
COPY --from=builder /usr/src/app/target/USD_GBP_Converter-1.0-SNAPSHOT.jar /usr/app/USD_GBP_Converter-1.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/usr/app/USD_GBP_Converter-1.0-SNAPSHOT.jar"]
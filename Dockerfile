FROM openjdk:8-jdk-alpine

COPY ./target/product-market-1.0-SNAPSHOT.jar app/product-market-1.0.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/product-market-1.0.jar"]

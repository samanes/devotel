
FROM maven:3.9.4-eclipse-temurin-21 AS builder
WORKDIR /app

COPY shared ./shared
RUN mvn -f shared/pom.xml clean install -DskipTests

COPY user-service ./user-service
WORKDIR /app/user-service
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /app/user-service/target/user-service-0.0.1-SNAPSHOT.jar ./user-service.jar

EXPOSE 8081
ENTRYPOINT ["java","-jar","user-service.jar"]
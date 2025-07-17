FROM maven:3.9.4-eclipse-temurin-21 AS builder
WORKDIR /app

COPY shared ./shared
RUN mvn -f shared/pom.xml clean install -DskipTests

COPY profile-service ./profile-service
WORKDIR /app/profile-service
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/profile-service/target/profile-service-0.0.1-SNAPSHOT.jar ./profile-service.jar

EXPOSE 8082
ENTRYPOINT ["java","-jar","profile-service.jar"]
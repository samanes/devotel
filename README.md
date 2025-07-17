# Devotel Microservices

## Overview
This repository contains two Spring Boot microservices:
- **User Service**: Provides SOAP and REST endpoints to manage users.
- **Profile Service**: Provides REST endpoints for user profiles and integrates with **User Service** via SOAP.

## Technologies Used
- **Java 21**
- **Spring Boot 3.5.3**
- **Spring Data JPA**
- **Spring Web / Spring Web Services**
- **Springdoc OpenAPI (Swagger UI)**
- **Liquibase** for database migrations
- **H2** (in-memory database for development)
- **Maven** for build and dependency management
- **Docker & Docker Compose** for containerization

## Prerequisites
- JDK 21 or later
- Maven 3.8+
- Docker & Docker Compose (optional, for containerized setup)

## Running Locally (Without Docker)
1. **Clone the repository**
   ```bash
   git clone https://github.com/your-org/devotel.git
   cd devotel
   ```
2. **Build and install all modules**
   ```bash
   mvn clean install -DskipTests
   ```
3. **Run User Service**
   ```bash
   cd user-service
   mvn spring-boot:run
   ```
    - Swagger UI: `http://localhost:8081/swagger-ui/index.html`
4. **Run Profile Service**
   ```bash
   cd ../profile-service
   mvn spring-boot:run
   ```
    - Swagger UI: `http://localhost:8082/swagger-ui/index.html`

## Running with Docker
1. **Start all services**
   ```bash
   docker-compose up --build
   ```
2. Services will be available at:
    - **User Service**: `http://localhost:8081`
    - **Profile Service**: `http://localhost:8082`
3. **Swagger UI**
    - User Service: `http://localhost:8081/swagger-ui/index.html`
    - Profile Service: `http://localhost:8082/swagger-ui/index.html`

## API Endpoints

### User Service
- **GET** `/users`
- **GET** `/users/{id}`

### Profile Service
- **POST** `/profiles`
    - Request body:
      ```json
      {
        "bio": "string",
        "location": "string",
        "age": 30,
        "userId": 1
      }
      ```
- **GET** `/profiles/{id}`

## Database Migrations
Liquibase changelogs are located in:
```
src/main/resources/db/changelog/db.changelog-master.yaml
```

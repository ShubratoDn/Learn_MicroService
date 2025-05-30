# Microservices Project

This project demonstrates a microservices architecture using Spring Boot, Spring Cloud, Eureka Service Registry, and Spring Cloud Gateway. It consists of several independent services that communicate with each other, each responsible for a specific domain.

---

## Table of Contents

- [Project Structure](#project-structure)
- [Tech Stack](#tech-stack)
- [Services Overview](#services-overview)
- [Ports](#ports)
- [How to Run](#how-to-run)
- [Service Communication](#service-communication)
- [Database](#database)
- [API Gateway Routing](#api-gateway-routing)
- [Contributing](#contributing)
- [License](#license)

---

## Project Structure

- `ServiceRegistry/` - Eureka server for service discovery.
- `ApiGateway/` - API Gateway for routing and entry point for all clients.
- `UserService/` - Manages user data and operations.
- `HotelService/` - Manages hotel data and operations.
- `RatingService/` - Manages ratings for hotels and users.

---

## Tech Stack

- **Java 17**
- **Spring Boot** (3.2.x/3.3.x)
- **Spring Cloud** (2023.0.x)
- **Spring Cloud Netflix Eureka** (Service Discovery)
- **Spring Cloud Gateway** (API Gateway)
- **Spring Data JPA** (Database access)
- **MySQL** (Database)
- **Lombok** (Boilerplate code reduction)
- **Maven** (Build tool)
- **OpenFeign** (Declarative REST client, where used)

---

## Services Overview

### 1. ServiceRegistry
- **Purpose:** Central Eureka server for service registration and discovery.
- **Tech:** Spring Boot, Spring Cloud Netflix Eureka Server

### 2. ApiGateway
- **Purpose:** Single entry point for all client requests, routes to appropriate microservice.
- **Tech:** Spring Boot, Spring Cloud Gateway, Eureka Client

### 3. UserService
- **Purpose:** Handles user-related operations (CRUD, etc).
- **Tech:** Spring Boot, Spring Data JPA, Eureka Client, MySQL

### 4. HotelService
- **Purpose:** Handles hotel-related operations (CRUD, etc).
- **Tech:** Spring Boot, Spring Data JPA, Eureka Client, MySQL

### 5. RatingService
- **Purpose:** Handles ratings for hotels and users.
- **Tech:** Spring Boot, Spring Data JPA, Eureka Client, MySQL

---

## Ports

| Service         | Port  |
|-----------------|-------|
| ServiceRegistry | 8761  |
| ApiGateway      | 8084  |
| UserService     | 8081  |
| HotelService    | 8082  |
| RatingService   | 8083  |

---

## How to Run

### Prerequisites

- Java 17+
- Maven
- MySQL (running and accessible)

### Steps

1. **Clone the repository:**
   ```bash
   git clone <repo-url>
   cd Learn_MicroService
   ```

2. **Configure MySQL:**
   - Ensure MySQL is running.
   - Update `application.properties` in each service (`UserService`, `HotelService`, `RatingService`) with your MySQL credentials and database name.

3. **Start the services in order:**

   - **ServiceRegistry** (Eureka Server)
     ```bash
     cd ServiceRegistry
     mvn spring-boot:run
     ```
   - **ApiGateway**
     ```bash
     cd ../ApiGateway
     mvn spring-boot:run
     ```
   - **UserService**
     ```bash
     cd ../UserService
     mvn spring-boot:run
     ```
   - **HotelService**
     ```bash
     cd ../HotelService
     mvn spring-boot:run
     ```
   - **RatingService**
     ```bash
     cd ../RatingService
     mvn spring-boot:run
     ```

4. **Access the services:**
   - Eureka Dashboard: [http://localhost:8761](http://localhost:8761)
   - API Gateway: [http://localhost:8084](http://localhost:8084)

---

## Service Communication

- All services register themselves with Eureka.
- Services communicate via REST, using service names (load-balanced by Eureka).
- API Gateway routes external requests to the appropriate service.

---

## Database

- Each service (`UserService`, `HotelService`, `RatingService`) uses its own MySQL database.
- Configure DB connection in each service's `application.properties`.

---

## API Gateway Routing

Example routes (from `ApiGateway/src/main/resources/application.yml`):

```yaml
  - Path=/api/users/**     -> UserService
  - Path=/api/hotels/**    -> HotelService
  - Path=/api/ratings/**   -> RatingService
```

---

## Contributing

Feel free to fork, open issues, or submit pull requests!

---

## License

This project is open source and available under the [MIT License](LICENSE). 
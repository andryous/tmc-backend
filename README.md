# The Moving Company - Backend API

[![Java](https://img.shields.io/badge/Java-21-blue.svg?logo=openjdk&logoColor=white)](https://www.java.com)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.6-brightgreen.svg?logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-336791.svg?logo=postgresql&logoColor=white)](https://www.postgresql.org)
[![Maven](https://img.shields.io/badge/Maven-3.9-red.svg?logo=apache-maven&logoColor=white)](https://maven.apache.org)

A secure RESTful API for a service management platform, built with Spring Boot and secured with JWT.

**[Live Demo]** (https://tmc-dashboard-new.onrender.com/login)

---

### ► Key Features
- JWT Authentication & Role-Based Authorization (`ROLE_CONSULTANT`).
- Full CRUD operations for Orders, Customers, and Consultants.
- Archiving/Restoring functionality for customers.
- Aggregated statistics endpoint for the dashboard.

### ► Tech Stack
- **Backend:** Java 21, Spring Boot, Spring Security, Spring Data JPA
- **Database:** PostgreSQL (Production), H2 (Development)
- **Security:** JSON Web Tokens (JJWT)
- **Build:** Apache Maven
- **API Docs:** Swagger (OpenAPI 3)

---

### ► Getting Started

**1. Prerequisites**
- Java JDK 21
- Maven 3.9+
- PostgreSQL 17

**2. Clone & Build**
```bash
git clone https://github.com/andryous/tmc-backend.git
./mvnw clean install
```

**3. Environment Configuration.**  
Configure these variables in your environment or application-prod.properties.
```properties
# Production Database
spring.datasource.url=jdbc:postgresql://<host>:<port>/<db_name>
spring.datasource.username=<db_username>
spring.datasource.password=<db_password>

# JWT Secret Key (Base64 Encoded)
application.security.jwt.secret-key=<your_base64_encoded_secret_key>
```

**4. Run the Application**  
Dev Profile (H2 in-memory DB):
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Prod Profile (PostgreSQL):
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

### ► API Documentation
For a detailed API reference and interactive testing, view the Swagger UI documentation available at http://localhost:8088/swagger-ui/index.html

### 👤 Author
Claudio Rodriguez (Educational Project for Crayon Consulting Academy)
# The Moving Company – Backend

## Project Overview

The Moving Company backend is a REST API that powers a moving, packing, and cleaning service management platform.  
Its main purpose is to allow **sales consultants** to create, view, update, and manage service orders for customers.

The project was designed to be part of a complete system, including:
- **Public website** – Where customers can learn about services and request quotes.
- **Admin dashboard** – Where consultants manage orders and customers.
- **Backend API** – The system you are currently viewing.

### Problem Statement

In service-oriented businesses like moving companies, sales consultants need a reliable system to handle customer information and service orders efficiently.  
Before this system, there was no centralized API or database structure to store, search, and update orders.

### Solution

This backend provides:
- A well-structured **PostgreSQL database** (H2 for local development).
- A set of **REST API endpoints** to handle:
    - Orders
    - Customers
    - Consultants
- Full documentation via **Swagger UI**.
- Separate **profiles** for development and production environments.
- Deployment on **Render.com** for free public access.

### Implementation Process

1. **Planning & Database Design**
    - Initial database created using **H2** for fast local testing.
    - Entity-relationship diagram defined (Orders, Persons, Roles, Status, Service Types).
    - Schema initialized with SQL scripts.

2. **API Development**
    - Built with **Java 21** and **Spring Boot 3.x**.
    - Used **Spring Data JPA** for database interactions.
    - Controllers, Services, and Repositories structured in a layered architecture.
    - Swagger OpenAPI configuration added for automatic API documentation.

3. **Migration to PostgreSQL**
    - Exported data from H2 to `.sql` backup.
    - Created PostgreSQL 16 instance in Render.
    - Updated Spring Boot configuration to use environment variables for secure DB connection.

4. **Deployment**
    - Backend deployed to Render using the production profile.
    - Internal Database URL used for secure connection between backend and DB.
    - Public URL available for integration with the admin dashboard.

5. **Testing & Verification**
    - API endpoints tested locally with H2 and remotely with PostgreSQL.
    - Swagger used to verify requests and responses.
    - Edge cases tested for data validation and error handling.

---


## 🛠 Build & Installation

### Prerequisites
- **Java JDK 21** 
- **Apache Maven 3.9.10**
- **PostgreSQL 16** (for production) or H2 (for local development)
- **Git** (to clone the repository)

### Clone the repository
```bash
git clone https://github.com/andryous/tmc-backend.git
cd tmc-backend
```
### Build the project
Run the following command to compile the project and download all dependencies:
```bash
./mvnw clean install
```
### Running the Application
1. Development (Local, H2 Database)
   This mode uses an in-memory H2 database, ideal for quick local testing:
``` bash
   ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
   ```
The application will be available at:
http://localhost:8088

2. Production (PostgreSQL on Render)
   To run in the same environment as Render.com, make sure to set the following environment variables:
``` bash
SPRING_DATASOURCE_URL=<postgres_url>
SPRING_DATASOURCE_USERNAME=<username>
SPRING_DATASOURCE_PASSWORD=<password>
```
Then run:

``` bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

💡 Note: The default port is 8088 in both environments. 

---

## 🧪 Testing

### 1. Using Swagger UI
Swagger is integrated and automatically available when the application is running.  
It provides interactive API documentation where you can execute requests directly from your browser.

- **Local (H2):** [http://localhost:8088/swagger-ui/index.html](http://localhost:8088/swagger-ui/index.html)
- **Production (Render):** https://tmc-backend-8xi6.onrender.com/swagger-ui/index.html

Swagger allows you to:
- View all available endpoints.
- Execute `GET`, `POST`, `PUT`, `PATCH`, and `DELETE` requests.
- See request/response examples.
- Test with real or seed data.

---

### 2. Using Postman
You can also test the API using Postman:
1. Import the Swagger OpenAPI specification:  
   `<your_base_url>/v3/api-docs`
2. Use the generated Postman collection to run requests.
3. Set the environment variables (`base_url`, `auth` if needed).

---

### 3. Using Seed Data
In **development mode**, the application uses the `DataInitializer` class to seed sample data into the H2 database.  
This allows immediate testing without manual data creation.

---

## 📡 API Endpoints

| Method | Endpoint                               | Description                             |
|--------|----------------------------------------|-----------------------------------------|
| GET    | `/api/orders`                          | List all orders                         |
| POST   | `/api/orders`                          | Create a new order                      |
| GET    | `/api/orders/{id}`                     | Get an order by ID                      |
| PUT    | `/api/orders/{id}`                     | Update an existing order                |
| DELETE | `/api/orders/{id}`                     | Delete an order                         |
| GET    | `/api/orders/search`                   | Search orders by multiple parameters    |
| GET    | `/api/orders/by-status/{status}`       | Get orders by status                    |
| GET    | `/api/orders/by-service-type/{type}`   | Get orders by service type              |
| GET    | `/api/orders/by-customer/{id}`         | Get orders by customer ID               |
| GET    | `/api/orders/by-consultant/{id}`       | Get orders by consultant ID             |
| GET    | `/api/persons`                         | List all persons                        |
| POST   | `/api/persons`                         | Create a new person                     |
| GET    | `/api/persons/{id}`                    | Get a person by ID                      |
| PUT    | `/api/persons/{id}`                    | Update a person                         |
| DELETE | `/api/persons/{id}`                    | Delete a person                         |
| GET    | `/api/persons/by-role/{role}`          | Get persons by role                     |
| GET    | `/api/persons/by-role/CUSTOMER/archived` | Get archived customers                |
| POST   | `/api/persons/login`                   | Fake login (demo user only)              |
| GET    | `/active-profile`                      | Get the active Spring profile           |

For complete details and live testing, visit the **Swagger UI** page in your running environment.

---
## 👤 Credits
**Author:** Claudio Rodriguez  
**Context:** Crayon Consulting Academy Bootcamp – Fullstack Developer Program (Educational Project)






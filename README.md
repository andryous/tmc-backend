# The Moving Company – Backend

## Project Overview

The Moving Company backend is a REST API that powers a moving, packing, and cleaning service management platform.  
Its main purpose is to allow **sales consultants** to create, view, update, and manage service orders for customers.

The project was designed to be part of a complete system, including:
- **Public website** – Where customers can learn about services and request quotes.
- **Admin dashboard** – Where consultants manage orders and customers.
- **Backend API** – The system you are currently viewing.


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

2. Production (PostgreSQL)

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
- **Local:** swagger-ui/index.html)
- **Production (Render):** https://tmc-backend-8xi6.onrender.com/swagger-ui/index.html


### 2. Using Postman
1. Import the Swagger OpenAPI specification:  
   `<your_base_url>/v3/api-docs`
2. Use the generated Postman collection to run requests.
3. Set the environment variables (`base_url`, `auth` if needed).

---

### 3. Using Seed Data
In **development mode**, the application uses the `DataInitializer` class to seed sample data into the H2 database.  
This allows immediate testing without manual data creation.

---
## 👤 Credits
**Author:** Claudio Rodriguez  
**Context:** Crayon Consulting Academy Bootcamp – Fullstack Developer Program (Educational Project)






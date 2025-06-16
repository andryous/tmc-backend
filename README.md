# The Moving Company – Order Placement System (Backend)

This is a Spring Boot backend application designed for managing service orders (moving, packing, cleaning) for a moving company. Sales consultants can create, update, and manage orders on behalf of customers. The system supports full CRUD operations, environment-specific configurations, and API documentation via Swagger.

## 🚀 Features

- Create, retrieve, update, and delete `Person` and `Order` records
- Assign service type (`MOVING`, `PACKING`, `CLEANING`) and track status (`PENDING`, `IN_PROGRESS`, `COMPLETED`, `CANCELLED`)
- Associate orders with customers (`Person`)
- Data validation and centralized error handling
- Profile-based configuration (`dev`, `prod`) using Spring Boot
- Interactive API documentation via Swagger UI
- Clean code architecture: Controller → Service → Repository → Model

## 🛠️ Technology Stack

- Java 21 (Zulu)
- Spring Boot 3.2.5
- Maven
- H2 in-memory database (dev)
- Swagger (OpenAPI 3)
- IntelliJ IDEA 2025.1.2


## 📁 Folder Structure

```plaintext
src/main/java/org/example/themovingcompany/
│
├── controller/        # REST endpoints
├── service/           # Business logic
├── repository/        # Data access
├── model/             # Domain entities & enums
├── exception/         # Custom error handling
└── TheMovingCompanyApplication.java
```


## 📦 Profiles

The app supports two runtime profiles with independent ports:

| Profile | Port  | Database              | Logging Level |
|---------|-------|------------------------|----------------|
| `dev`   | 8088  | In-memory (H2)         | DEBUG          |
| `prod`  | 8081  | Prepared for real use  | INFO           |

**How to activate:**  
Set an environment variable before running:
```bash

SPRING_PROFILES_ACTIVE=dev   # or prod
```
📚 API Documentation
After starting the server, access Swagger UI at:

http://localhost:8088/swagger-ui/index.html    # dev
http://localhost:8081/swagger-ui/index.html    # prod

📄 Future Development
The current version focuses on managing orders from the customer's side. Consultant-specific features (e.g., consultant-assigned orders, role-based filtering) are planned for future iterations.

⚠️ Notes
Data is stored in memory (H2) and resets with each restart.

To persist data, switch to jdbc:h2:file:./data/devdb in the profile configuration.

🧑 Author
This project was developed as part of a Java Spring Boot internship program under Crayon Academy, with the goal of demonstrating backend fundamentals and clean architecture in a practical business context.




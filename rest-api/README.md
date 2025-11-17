# REST API Module

## Giới thiệu
Module REST API demo các best practices cho RESTful API trong Spring Boot:
- CRUD operations đầy đủ
- Request/Response validation
- Exception handling với GlobalExceptionHandler
- OpenAPI/Swagger documentation
- H2 in-memory database

## Công nghệ sử dụng
- Spring Boot 3.4.0
- Spring Data JPA
- Spring Validation
- SpringDoc OpenAPI (Swagger)
- H2 Database
- Lombok

## Cách chạy

### 1. Build và chạy từ module directory
```bash
cd rest-api
mvn clean install
mvn spring-boot:run
```

### 2. Hoặc chạy từ root project
```bash
mvn clean install -pl rest-api -am
mvn spring-boot:run -pl rest-api
```

## Endpoints

Base URL: `http://localhost:8081`

### API Documentation
- Swagger UI: http://localhost:8081/swagger-ui.html
- OpenAPI Docs: http://localhost:8081/api-docs
- H2 Console: http://localhost:8081/h2-console

### User APIs

#### 1. Get All Users
```bash
GET http://localhost:8081/api/users

# Response:
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "bio": "Software Engineer"
  }
]
```

#### 2. Get User by ID
```bash
GET http://localhost:8081/api/users/1

# Response:
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "bio": "Software Engineer"
}
```

#### 3. Create User
```bash
POST http://localhost:8081/api/users
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "bio": "Software Engineer"
}

# Response (201 Created):
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "bio": "Software Engineer"
}
```

#### 4. Update User
```bash
PUT http://localhost:8081/api/users/1
Content-Type: application/json

{
  "name": "Jane Doe",
  "email": "jane@example.com",
  "bio": "Senior Engineer"
}

# Response:
{
  "id": 1,
  "name": "Jane Doe",
  "email": "jane@example.com",
  "bio": "Senior Engineer"
}
```

#### 5. Delete User
```bash
DELETE http://localhost:8081/api/users/1

# Response: 204 No Content
```

## Test với cURL

```bash
# Create user
curl -X POST http://localhost:8081/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com","bio":"Developer"}'

# Get all users
curl http://localhost:8081/api/users

# Get user by ID
curl http://localhost:8081/api/users/1

# Update user
curl -X PUT http://localhost:8081/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Jane Doe","email":"jane@example.com","bio":"Senior Dev"}'

# Delete user
curl -X DELETE http://localhost:8081/api/users/1
```

## Validation Examples

### Invalid Email
```bash
POST http://localhost:8081/api/users
Content-Type: application/json

{
  "name": "John",
  "email": "invalid-email",
  "bio": "Test"
}

# Response (400 Bad Request):
{
  "timestamp": "2024-01-01T10:00:00",
  "status": 400,
  "error": "Validation Failed",
  "message": "Input validation failed",
  "path": "/api/users",
  "validationErrors": {
    "email": "Email should be valid"
  }
}
```

### Duplicate Email
```bash
# Response (409 Conflict):
{
  "timestamp": "2024-01-01T10:00:00",
  "status": 409,
  "error": "Conflict",
  "message": "Email already exists: john@example.com",
  "path": "/api/users"
}
```

## Tính năng chính

### 1. Validation
- Request validation với Bean Validation annotations
- Custom error messages
- Field-level validation errors

### 2. Exception Handling
- `ResourceNotFoundException` (404)
- `DuplicateResourceException` (409)
- Validation errors (400)
- Global exception handler

### 3. OpenAPI Documentation
- Swagger UI tự động
- API documentation đầy đủ
- Request/Response examples

### 4. Best Practices
- Service layer pattern
- DTO pattern
- Repository pattern
- RESTful conventions
- HTTP status codes đúng chuẩn

## Cấu trúc code

```
rest-api/
├── config/
│   └── OpenApiConfig.java          # Swagger configuration
├── controller/
│   └── UserController.java         # REST endpoints
├── dto/
│   ├── UserRequest.java            # Request DTO
│   └── UserResponse.java           # Response DTO
├── entity/
│   └── User.java                   # JPA Entity
├── exception/
│   ├── ResourceNotFoundException.java
│   ├── DuplicateResourceException.java
│   ├── ErrorResponse.java
│   └── GlobalExceptionHandler.java # Exception handling
├── repository/
│   └── UserRepository.java         # Data access
└── service/
    └── UserService.java            # Business logic
```

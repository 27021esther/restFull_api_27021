 User Profile API - System Architecture Documentation

 1. Project Overview

 1.1 Project Information

- Project Name: RESTFULL_api
- Version: 1.0.0
- Framework: Spring Boot 3.x
- Java Version: 21
- Build Tool: Maven
- API Type: RESTful Web Service

 1.2 Project Description
A comprehensive RESTful API for managing user profiles with features including CRUD operations, search functionality, user activation/deactivation, and filtering by various criteria. All responses are wrapped in a standardized `ApiResponse` format for consistency.

---
 2. System Architecture

2.1 Architecture Pattern
This project follows a Layered Architecture with three main layers:
```
┌─────────────────────────────────────┐
│     Presentation Layer              │
│   (REST Controllers)                │
│   - UserProfileController           │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│     Business Logic Layer            │
│   (Controller handles logic)        │
│   - Validation                      │
│   - Business Rules                  │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│     Data Layer                      │
│   (In-Memory Storage)               │
│   - ArrayList<UserProfile>          │
└─────────────────────────────────────┘
```

 2.2 Architecture Diagram
```
┌──────────────────────────────────────────────────────────┐
│                    Client Layer                          │
│  (Postman, Browser, Mobile App, Web Frontend)            │
└──────────────────────────────────────────────────────────┘
                        ↓ HTTP/REST
┌──────────────────────────────────────────────────────────┐
│                  Spring Boot Application                 │
│ ┌────────────────────────────────────────────────────┐   │
│ │           Controller Layer                         │   │
│ │  @RestController                                   │   │
│ │  - UserProfileController                           │   │
│ │    • Request Mapping                               │   │
│ │    • Input Validation                              │   │
│ │    • Response Formatting                           │   │
│ └────────────────────────────────────────────────────┘   │
│                        ↓                                  │
│ ┌────────────────────────────────────────────────────┐   │
│ │           Model/Entity Layer                       │   │
│ │  - UserProfile (POJO)                              │   │
│ │    • Data Structure                                │   │
│ │    • Encapsulation                                 │   │
│ └────────────────────────────────────────────────────┘   │
│                        ↓                                  │
│ ┌────────────────────────────────────────────────────┐   │
│ │           Response Layer                           │   │
│ │  - ApiResponse<T> (Generic Wrapper)                │   │
│ │    • success (boolean)                             │   │
│ │    • message (String)                              │   │
│ │    • data (T)                                      │   │
│ └────────────────────────────────────────────────────┘   │
│                        ↓                                  │
│ ┌────────────────────────────────────────────────────┐   │
│ │           Data Storage Layer                       │   │
│ │  - In-Memory ArrayList                             │   │
│ │    • List<UserProfile> userProfiles                │   │
│ │    • Auto-increment ID generator                   │   │
│ └────────────────────────────────────────────────────┘   │
└──────────────────────────────────────────────────────────┘
```

---

3. Package Structure

 3.1 Directory Layout
```
src/
└── main/
    ├── java/
    │   └── com/
    │       └── api/
    │           └── userprofile/
    │               ├── UserProfileApplication.java    # Main Entry Point
    │               ├── controller/                    # REST Controllers
    │               │   └── UserProfileController.java
    │               ├── model/                         # Domain Models
    │               │   └── UserProfile.java
    │               └── response/                      # Response DTOs
    │                   └── ApiResponse.java
    └── resources/
        └── application.properties                     # Configuration
```

 3.2 Package Responsibilities

| Package | Purpose | Components |
|---------|---------|------------|
| `com.api.userprofile` | Root package & application entry | UserProfileApplication.java |
| `controller` | Handle HTTP requests & responses | UserProfileController |
| `model` | Data models/entities | UserProfile |
| `response` | Response wrapper classes | ApiResponse |

---

 4. Component Details

4.1 Controller Layer

 UserProfileController
Location:** `com.api.userprofile.controller.UserProfileController`

Responsibilities:**
- Handle incoming HTTP requests
- Route requests to appropriate methods
- Validate input data
- Format responses using ApiResponse wrapper
- Return appropriate HTTP status codes

Key Annotations:
- `@RestController` - Marks as REST controller
- `@RequestMapping("/api/users")` - Base URL mapping
- `@GetMapping`, `@PostMapping`, `@PutMapping`, `@PatchMapping`, `@DeleteMapping` - HTTP method mappings
- `@PathVariable` - Extract values from URL path
- `@RequestParam` - Extract query parameters
- `@RequestBody` - Parse JSON request body

Endpoints:*12 total endpoints (See API Documentation section)

 4.2 Model Layer

 UserProfile
Location:** `com.api.userprofile.model.UserProfile`

Purpose:** Represents a user profile entity

Attributes:
```java
- userId: Long           // Unique identifier
- username: String       // Unique username
- email: String         // User email address
- fullName: String      // Full name of user
- age: int              // User age
- country: String       // User's country
- bio: String           // User biography
- active: boolean       // Account status
```

Design Pattern:Plain Old Java Object (POJO)

 4.3 Response Layer

 ApiResponse<T>
Location: `com.api.userprofile.response.ApiResponse`

Purpose: Generic wrapper for all API responses

Structure:
```java
{
    "success": boolean,    // Operation success status
    "message": String,     // Descriptive message
    "data": T             // Generic data payload
}
```

Benefits:
- Consistent response format across all endpoints
- Clear success/failure indication
- Descriptive error/success messages
- Type-safe generic data field

---
 5. Data Flow

 5.1 Request Flow (Example: Get User by ID)
```
1. Client sends request
   ↓
   GET http://localhost:8080/api/users/1
   
2. Spring DispatcherServlet receives request
   ↓
   
3. Routes to UserProfileController.getUserById(1)
   ↓
   
4. Controller searches in-memory list
   ↓
   List<UserProfile> → Stream → Filter by ID
   
5. Controller creates ApiResponse
   ↓
   new ApiResponse<>(true, "User profile retrieved successfully", user)
   
6. Spring converts to JSON
   ↓
   {
     "success": true,
     "message": "User profile retrieved successfully",
     "data": {
       "userId": 1,
       "username": "john_doe",
       ...
     }
   }
   
7. Response sent to client with HTTP 200 OK
```

### 5.2 Create User Flow
```
Client → POST /api/users + JSON body
   ↓
Controller receives UserProfile object
   ↓
Validate username uniqueness
   ↓
Assign new userId (auto-increment)
   ↓
Set active = true
   ↓
Add to in-memory list
   ↓
Wrap in ApiResponse
   ↓
Return HTTP 201 Created + JSON response
```

---

## 6. API Documentation

### 6.1 Base URL
```
http://localhost:8080/api/users
```

### 6.2 Endpoints Summary

| Method | Endpoint | Description | Status Codes |
|--------|----------|-------------|--------------|
| GET | `/api/users` | Get all users | 200 |
| GET | `/api/users/{userId}` | Get user by ID | 200, 404 |
| GET | `/api/users/username/{username}` | Get user by username | 200, 404 |
| GET | `/api/users/country/{country}` | Get users by country | 200 |
| GET | `/api/users/age-range?min={min}&max={max}` | Filter by age range | 200 |
| GET | `/api/users/active` | Get active users | 200 |
| GET | `/api/users/search?keyword={keyword}` | Search users | 200 |
| POST | `/api/users` | Create new user | 201, 409 |
| PUT | `/api/users/{userId}` | Update user | 200, 404 |
| PATCH | `/api/users/{userId}/activate` | Activate user | 200, 404 |
| PATCH | `/api/users/{userId}/deactivate` | Deactivate user | 200, 404 |
| DELETE | `/api/users/{userId}` | Delete user | 204, 404 |

6.3 HTTP Status Codes Used

| Code | Meaning | Usage |
|------|---------|-------|
| 200 | OK | Successful GET, PUT, PATCH |
| 201 | Created | Successful POST |
| 204 | No Content | Successful DELETE |
| 404 | Not Found | Resource doesn't exist |
| 409 | Conflict | Username already exists |

6.4 Sample Request/Response

 Create User (POST)

Request:
```http
POST /api/users HTTP/1.1
Content-Type: application/json

{
  "username": "new_user",
  "email": "new@example.com",
  "fullName": "New User",
  "age": 28,
  "country": "USA",
  "bio": "New developer"
}
```

**Response:**
```http
HTTP/1.1 201 Created
Content-Type: application/json

{
  "success": true,
  "message": "User profile created successfully",
  "data": {
    "userId": 6,
    "username": "new_user",
    "email": "new@example.com",
    "fullName": "New User",
    "age": 28,
    "country": "USA",
    "bio": "New developer",
    "active": true
  }
}
```

---

## 7. Data Storage

### 7.1 Storage Mechanism
- **Type:** In-Memory Storage
- **Implementation:** `ArrayList<UserProfile>`
- **Scope:** Application lifetime
- **Persistence:** None (data lost on restart)

### 7.2 ID Generation Strategy
```java
private Long nextId = 1L;

// Auto-increment on each new user
userProfile.setUserId(nextId++);
```

### 7.3 Sample Data
The application initializes with 5 sample users:
1. john_doe (USA, age 25, active)
2. jane_smith (Canada, age 30, active)
3. bob_wilson (UK, age 28, active)
4. alice_brown (USA, age 35, inactive)
5. charlie_davis (Australia, age 22, active)

---

## 8. Technology Stack

### 8.1 Core Technologies

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 17+ | Programming Language |
| Spring Boot | 3.x | Application Framework |
| Spring Web | 3.x | REST API Development |
| Maven | 3.x | Build & Dependency Management |
| Jackson | (included) | JSON Serialization |
| Tomcat | (embedded) | Web Server |

### 8.2 Dependencies (pom.xml)
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

---

## 9. Configuration

### 9.1 Application Properties

**File:** `src/main/resources/application.properties`
```properties
# Server Configuration
server.port=8080

# Application Name
spring.application.name=userprofile-api
```

### 9.2 Spring Boot Auto-Configuration
- Embedded Tomcat server
- Jackson JSON mapper
- Spring MVC configuration
- Exception handling

---

## 10. Design Patterns Used

### 10.1 MVC Pattern (Modified)
- **Model:** UserProfile entity
- **View:** JSON responses
- **Controller:** UserProfileController

### 10.2 DTO Pattern
- ApiResponse acts as a Data Transfer Object
- Wraps all responses consistently

### 10.3 Generic Programming
- `ApiResponse<T>` uses Java Generics
- Type-safe responses

### 10.4 Builder Pattern (Implicit)
- Spring uses builder pattern for ResponseEntity

---

## 11. Security Considerations

### 11.1 Current Implementation
⚠️ **Note:** This is a learning project with no security implemented

**Missing Security Features:**
- No authentication
- No authorization
- No input sanitization
- No rate limiting
- No HTTPS enforcement

### 11.2 Production Recommendations

For production deployment, consider adding:

1. **Spring Security**
   - JWT or OAuth2 authentication
   - Role-based access control

2 Input Validation
```java
   @Valid @RequestBody UserProfile userProfile
```
   - Use JSR-303 annotations (@NotNull, @Email, etc.)

3. **HTTPS**
   - SSL/TLS configuration

4. **CORS Configuration**
   - Allow specific origins only

---

## 12. Testing Strategy

### 12.1 Manual Testing with Postman

**Test Cases:**
1. Create 3 new users
2. Get all users
3. Get user by ID (existing and non-existing)
4. Search by username
5. Filter by country
6. Filter by age range
7. Get active users only
8. Update user details
9. Activate/deactivate user
10. Delete user

### 12.2 Expected Test Results

| Test | Expected Result |
|------|----------------|
| POST valid user | 201 Created + user data |
| POST duplicate username | 409 Conflict |
| GET existing user | 200 OK + user data |
| GET non-existing user | 404 Not Found |
| DELETE existing user | 204 No Content |

---

## 13. Error Handling

### 13.1 Error Response Format

All errors follow the same ApiResponse structure:
```json
{
  "success": false,
  "message": "Error description here",
  "data": null
}
```
 13.2 Common Error Scenarios

| Scenario | Status Code | Message |
|----------|-------------|---------|
| User not found | 404 | "User profile not found with ID: X" |
| Duplicate username | 409 | "Username already exists: X" |
| Invalid request | 400 | Auto-generated by Spring |

---

 14. Performance Considerations

14.1 Current Limitations

Scalability Issues:
- In-memory storage (not suitable for production)
- No pagination (loads all users)
- Linear search O(n) complexity
- Single-threaded operations

14.2 Optimization Recommendations

For production:
1. Use database (PostgreSQL, MySQL)
2. Implement pagination
3. Add caching (Redis)
4. Use indexed database queries
5. Implement connection pooling

---

15. Future Enhancements

15.1 Planned Features

1. Database Integration
   - Spring Data JPA
   - PostgreSQL or MySQL

2. Authentication & Authorization
   - Spring Security
   - JWT tokens

3. Additional Endpoints
   - Bulk user operations
   - User statistics
   - Profile picture upload

4. Advanced Features
   - Email verification
   - Password reset
   - User preferences
   - Activity logging

5. Documentation
   - Swagger/OpenAPI integration
   - Interactive API docs

---

 16. Build & Deployment

16.1 Build Commands
```bash
 Clean and build
mvn clean install

 Run application
mvn spring-boot:run

 Package as JAR
mvn package

 Run JAR
java -jar target/userprofile-0.0.1-SNAPSHOT.jar
```

 16.2 Environment Variables

Currently uses default values. For production, externalize:
- `SERVER_PORT` (default: 8080)
- Database credentials
- API keys

---

 17. Troubleshooting

17.1 Common Issues

| Issue | Solution |
|-------|----------|
| Port 8080 already in use | Change port in application.properties |
| Application won't start | Check Java version (17+) |
| 404 on all endpoints | Verify base URL and context path |
| JSON parsing errors | Check Content-Type header |

 17.2 Debugging

Enable debug logging:
```properties
logging.level.com.api.userprofile=DEBUG
```

---

 18. Project Maintainers

Student Information
- Student ID: 27021
- Branch: restFull_api_27021
- Course: REST API Development
- Framework: Spring Boot

---

. Version History

| Version | Date | Changes |
|---------|------|---------|
| 1.0.0 | 2024 | Initial release with all bonus features |

---

. References & Resources

 Documentation
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Web MVC](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html)
- [RESTful API Design](https://restfulapi.net/)

20.2 Tools Used
 vs/code
 Postman for API testing
 Maven for dependency management



End of Architecture Documentation**
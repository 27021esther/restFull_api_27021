User Profile API - Complete Documentation

 Table of Contents
1. [Project Overview]
2. [Quick Start Guide]
3. [API Endpoints]
4. [Request & Response Examples]
5. [Testing Guide]
6. [Project Structure][Technologies Used]


---

Project Overview

Description
A comprehensive RESTful API for managing user profiles with full CRUD operations, search capabilities, filtering, and user status management. All responses are wrapped in a standardized `ApiResponse` format for consistency.

 Key Features
- ✅ Complete CRUD operations (Create, Read, Update, Delete)
- ✅ User search by username, country, age range, and keywords
- ✅ User activation/deactivation
- ✅ Filter active users
- ✅ Standardized API response format
- ✅ Proper HTTP status codes
- ✅ In-memory data storage with sample data

 Project Information
- Framework:** Spring Boot 3.x
- Language:** Java 17+
- Build Tool:** Maven
- Server Port:** 8080
- Base URL:** `http://localhost:8080/api/users`

---

 Quick Start Guide

 Prerequisites
- Java 17 or higher
- Maven 3.6+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)
- Postman (for testing)

Installation Steps

1. Create Spring Boot Project
Go to [Spring Initializr](https://start.spring.io) and configure:
- Project: Maven
- Language: Java
- Spring Boot: 3.2.x
- Group: com.api
- Artifact: userprofile
- Dependencies: Spring Web

 2. Setup Project Structure
```
bonus-userprofile-api/
├── src/main/java/com/api/userprofile/
│   ├── UserProfileApplication.java
│   ├── controller/
│   │   └── UserProfileController.java
│   ├── model/
│   │   └── UserProfile.java
│   └── response/
│       └── ApiResponse.java
├── src/main/resources/
│   └── application.properties
├── pom.xml
├── README.md
├── architecture.md
└── index.md
```

 3. Run the Application

Using Maven:
```bash
mvn spring-boot:run
```

Using IDE:
- Right-click on `UserProfileApplication.java`
- Select "Run" or "Run as Spring Boot App"

Using JAR:
```bash
mvn clean package
java -jar target/userprofile-0.0.1-SNAPSHOT.jar
```

 4. Verify Application is Running
Open browser and navigate to:
```
http://localhost:8080/api/users
```

You should see a JSON response with sample users.

---

 API Endpoints

 Base URL
```
http://localhost:8080/api/users
```

 Endpoints Summary

| # | Method | Endpoint | Description |
|---|--------|----------|-------------|
| 1 | GET | `/api/users` | Get all users |
| 2 | GET | `/api/users/{userId}` | Get user by ID |
| 3 | GET | `/api/users/username/{username}` | Get user by username |
| 4 | GET | `/api/users/country/{country}` | Get users by country |
| 5 | GET | `/api/users/age-range?min={min}&max={max}` | Filter users by age range |
| 6 | GET | `/api/users/active` | Get only active users |
| 7 | GET | `/api/users/search?keyword={keyword}` | Search users by keyword |
| 8 | POST | `/api/users` | Create new user |
| 9 | PUT | `/api/users/{userId}` | Update user profile |
| 10 | PATCH | `/api/users/{userId}/activate` | Activate user account |
| 11 | PATCH | `/api/users/{userId}/deactivate` | Deactivate user account |
| 12 | DELETE | `/api/users/{userId}` | Delete user profile |

---

 Request & Response Examples

1. Get All Users

Request:
```http
GET http://localhost:8080/api/users
```

Response: `200 OK`
```json
{
  "success": true,
  "message": "User profiles retrieved successfully",
  "data": [
    {
      "userId": 1,
      "username": "john_doe",
      "email": "john@example.com",
      "fullName": "John Doe",
      "age": 25,
      "country": "USA",
      "bio": "Software developer passionate about Java",
      "active": true
    },
    {
      "userId": 2,
      "username": "jane_smith",
      "email": "jane@example.com",
      "fullName": "Jane Smith",
      "age": 30,
      "country": "Canada",
      "bio": "Full-stack developer and tech enthusiast",
      "active": true
    }
  ]
}
```

---

 2. Get User by ID

Request:
```http
GET http://localhost:8080/api/users/1
```

Response:** `200 OK`
```json
{
  "success": true,
  "message": "User profile retrieved successfully",
  "data": {
    "userId": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "fullName": "John Doe",
    "age": 25,
    "country": "USA",
    "bio": "Software developer passionate about Java",
    "active": true
  }
}
```

Error Response:** `404 Not Found`
```json
{
  "success": false,
  "message": "User profile not found with ID: 999",
  "data": null
}
```

---

 3. Get User by Username

Request:
```http
GET http://localhost:8080/api/users/username/john_doe
```

Response:`200 OK`
```json
{
  "success": true,
  "message": "User profile retrieved successfully",
  "data": {
    "userId": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "fullName": "John Doe",
    "age": 25,
    "country": "USA",
    "bio": "Software developer passionate about Java",
    "active": true
  }
}
```

---
 4. Get Users by Country

Request:
```http
GET http://localhost:8080/api/users/country/USA
```

Response: `200 OK`
```json
{
  "success": true,
  "message": "Found 2 user(s) from USA",
  "data": [
    {
      "userId": 1,
      "username": "john_doe",
      "email": "john@example.com",
      "fullName": "John Doe",
      "age": 25,
      "country": "USA",
      "bio": "Software developer passionate about Java",
      "active": true
    },
    {
      "userId": 4,
      "username": "alice_brown",
      "email": "alice@example.com",
      "fullName": "Alice Brown",
      "age": 35,
      "country": "USA",
      "bio": "Senior developer and team lead",
      "active": false
    }
  ]
}
```

---

5. Filter Users by Age Range

Request:
```http
GET http://localhost:8080/api/users/age-range?min=25&max=30
```

**Response:** `200 OK`
```json
{
  "success": true,
  "message": "Found 3 user(s) in age range 25-30",
  "data": [
    {
      "userId": 1,
      "username": "john_doe",
      "email": "john@example.com",
      "fullName": "John Doe",
      "age": 25,
      "country": "USA",
      "bio": "Software developer passionate about Java",
      "active": true
    },
    {
      "userId": 2,
      "username": "jane_smith",
      "email": "jane@example.com",
      "fullName": "Jane Smith",
      "age": 30,
      "country": "Canada",
      "bio": "Full-stack developer and tech enthusiast",
      "active": true
    },
    {
      "userId": 3,
      "username": "bob_wilson",
      "email": "bob@example.com",
      "fullName": "Bob Wilson",
      "age": 28,
      "country": "UK",
      "bio": "Data scientist with ML expertise",
      "active": true
    }
  ]
}
```

---

6. Get Active Users

**Request:**
```http
GET http://localhost:8080/api/users/active
```

**Response:** `200 OK`
```json
{
  "success": true,
  "message": "Found 4 active user(s)",
  "data": [
    {
      "userId": 1,
      "username": "john_doe",
      "email": "john@example.com",
      "fullName": "John Doe",
      "age": 25,
      "country": "USA",
      "bio": "Software developer passionate about Java",
      "active": true
    }
    // ... more active users
  ]
}
```

---

### 7. Search Users by Keyword

**Request:**
```http
GET http://localhost:8080/api/users/search?keyword=developer
```

**Response:** `200 OK`
```json
{
  "success": true,
  "message": "Found 4 user(s) matching keyword: developer",
  "data": [
    {
      "userId": 1,
      "username": "john_doe",
      "email": "john@example.com",
      "fullName": "John Doe",
      "age": 25,
      "country": "USA",
      "bio": "Software developer passionate about Java",
      "active": true
    }
    // ... more matching users
  ]
}
```

---

 8. Create New User

Request:
```http
POST http://localhost:8080/api/users
Content-Type: application/json

{
  "username": "sarah_jones",
  "email": "sarah@example.com",
  "fullName": "Sarah Jones",
  "age": 27,
  "country": "Germany",
  "bio": "Frontend developer specializing in React"
}
```

Response: `201 Created`
```json
{
  "success": true,
  "message": "User profile created successfully",
  "data": {
    "userId": 6,
    "username": "sarah_jones",
    "email": "sarah@example.com",
    "fullName": "Sarah Jones",
    "age": 27,
    "country": "Germany",
    "bio": "Frontend developer specializing in React",
    "active": true
  }
}
```

**Error Response (Duplicate Username):** `409 Conflict`
```json
{
  "success": false,
  "message": "Username already exists: john_doe",
  "data": null
}
```

---

### 9. Update User Profile

**Request:**
```http
PUT http://localhost:8080/api/users/1
Content-Type: application/json

{
  "username": "john_doe_updated",
  "email": "john.updated@example.com",
  "fullName": "John Doe Updated",
  "age": 26,
  "country": "USA",
  "bio": "Senior Software developer passionate about Java and Spring"
}
```

**Response:** `200 OK`
```json
{
  "success": true,
  "message": "User profile updated successfully",
  "data": {
    "userId": 1,
    "username": "john_doe_updated",
    "email": "john.updated@example.com",
    "fullName": "John Doe Updated",
    "age": 26,
    "country": "USA",
    "bio": "Senior Software developer passionate about Java and Spring",
    "active": true
  }
}
```

---

### 10. Activate User

**Request:**
```http
PATCH http://localhost:8080/api/users/4/activate
```

**Response:** `200 OK`
```json
{
  "success": true,
  "message": "User profile activated successfully",
  "data": {
    "userId": 4,
    "username": "alice_brown",
    "email": "alice@example.com",
    "fullName": "Alice Brown",
    "age": 35,
    "country": "USA",
    "bio": "Senior developer and team lead",
    "active": true
  }
}
```

---

### 11. Deactivate User

**Request:**
```http
PATCH http://localhost:8080/api/users/1/deactivate
```

**Response:** `200 OK`
```json
{
  "success": true,
  "message": "User profile deactivated successfully",
  "data": {
    "userId": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "fullName": "John Doe",
    "age": 25,
    "country": "USA",
    "bio": "Software developer passionate about Java",
    "active": false
  }
}
```

---

### 12. Delete User

**Request:**
```http
DELETE http://localhost:8080/api/users/5
```

**Response:** `204 No Content`
```json
{
  "success": true,
  "message": "User profile deleted successfully",
  "data": null
}
```

---

## Testing Guide

### Using Postman

#### Step 1: Import Postman Collection
1. Open Postman
2. Click "Import" button
3. Select the `postman_collection.json` file
4. All 12 endpoints will be imported

#### Step 2: Test Each Endpoint

**Test Order:**
1. ✅ GET all users (verify sample data)
2. ✅ GET user by ID (test with ID 1)
3. ✅ GET user by username (test with "john_doe")
4. ✅ POST create new user
5. ✅ GET all users (verify new user added)
6. ✅ PUT update user
7. ✅ GET users by country
8. ✅ GET users by age range
9. ✅ GET active users
10. ✅ PATCH deactivate user
11. ✅ PATCH activate user
12. ✅ DELETE user

#### Step 3: Expected Results

| Test | Expected Status | Expected Message |
|------|----------------|------------------|
| GET all users | 200 | "User profiles retrieved successfully" |
| GET by ID (exists) | 200 | "User profile retrieved successfully" |
| GET by ID (not exists) | 404 | "User profile not found with ID: X" |
| POST new user | 201 | "User profile created successfully" |
| POST duplicate | 409 | "Username already exists: X" |
| PUT update | 200 | "User profile updated successfully" |
| PATCH activate | 200 | "User profile activated successfully" |
| DELETE user | 204 | "User profile deleted successfully" |

### Using cURL

#### Get All Users
```bash
curl -X GET http://localhost:8080/api/users
```

#### Get User by ID
```bash
curl -X GET http://localhost:8080/api/users/1
```

#### Create New User
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "test_user",
    "email": "test@example.com",
    "fullName": "Test User",
    "age": 25,
    "country": "USA",
    "bio": "Test bio"
  }'
```

#### Update User
```bash
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_updated",
    "email": "john.new@example.com",
    "fullName": "John Updated",
    "age": 26,
    "country": "USA",
    "bio": "Updated bio"
  }'
```

#### Delete User
```bash
curl -X DELETE http://localhost:8080/api/users/5
```

---

## Project Structure

### File Organization
```
bonus-userprofile-api/
│
├── src/
│   ├── main/
│   │   ├── java/com/api/userprofile/
│   │   │   ├── UserProfileApplication.java       # Main entry point
│   │   │   ├── controller/
│   │   │   │   └── UserProfileController.java    # REST endpoints
│   │   │   ├── model/
│   │   │   │   └── UserProfile.java              # User entity
│   │   │   └── response/
│   │   │       └── ApiResponse.java              # Response wrapper
│   │   └── resources/
│   │       └── application.properties            # Configuration
│   └── test/
│
├── pom.xml                                       # Maven dependencies
├── README.md                                     # Project overview
├── architecture.md                               # Architecture docs
├── index.md                                      # This file
└── postman_collection.json                       # API tests
```

### Key Files Description

| File | Purpose |
|------|---------|
| `UserProfileApplication.java` | Spring Boot main class - application entry point |
| `UserProfileController.java` | REST controller with all 12 endpoints |
| `UserProfile.java` | POJO representing user entity |
| `ApiResponse.java` | Generic wrapper for consistent API responses |
| `application.properties` | Configuration (port, app name) |
| `pom.xml` | Maven dependencies and build configuration |

---

## Technologies Used

### Core Technologies
- **Java 17+** - Programming language
- **Spring Boot 3.x** - Application framework
- **Spring Web** - RESTful web services
- **Maven** - Build and dependency management
- **Jackson** - JSON serialization/deserialization
- **Embedded Tomcat** - Web server

### Development Tools
- **IntelliJ IDEA / Eclipse** - IDE
- **Postman** - API testing
- **Git** - Version control

### Key Dependencies (pom.xml)
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

---

## HTTP Status Codes Reference

| Code | Name | Usage in API |
|------|------|--------------|
| 200 | OK | Successful GET, PUT, PATCH operations |
| 201 | Created | Successful POST (user created) |
| 204 | No Content | Successful DELETE |
| 400 | Bad Request | Invalid request format |
| 404 | Not Found | User doesn't exist |
| 409 | Conflict | Duplicate username |
| 500 | Internal Server Error | Server-side errors |

---

## Sample Data

The application initializes with 5 sample users:

| ID | Username | Country | Age | Active |
|----|----------|---------|-----|--------|
| 1 | john_doe | USA | 25 | ✅ |
| 2 | jane_smith | Canada | 30 | ✅ |
| 3 | bob_wilson | UK | 28 | ✅ |
| 4 | alice_brown | USA | 35 | ❌ |
| 5 | charlie_davis | Australia | 22 | ✅ |

---

## Common Issues & Solutions

### Issue 1: Port 8080 Already in Use
**Solution:** Change port in `application.properties`
```properties
server.port=8081
```

### Issue 2: Application Won't Start
**Solution:** Verify Java version
```bash
java -version
# Should be 17 or higher
```

### Issue 3: 404 on All Endpoints
**Solution:** Verify base URL and ensure app is running
```
http://localhost:8080/api/users
```

### Issue 4: JSON Parsing Error
**Solution:** Add Content-Type header
```
Content-Type: application/json
```

---

## API Response Format

All endpoints return responses in this format:
```json
{
  "success": boolean,     // true = success, false = error
  "message": "string",    // Descriptive message
  "data": object/array    // Response payload (null on error)
}
```

**Success Example:**
```json
{
  "success": true,
  "message": "User profile created successfully",
  "data": { /* user object */ }
}
```

**Error Example:**
```json
{
  "success": false,
  "message": "User profile not found with ID: 999",
  "data": null
}
```

---

## Best Practices Followed

✅ RESTful API design principles  
✅ Proper HTTP methods (GET, POST, PUT, PATCH, DELETE)  
✅ Appropriate HTTP status codes  
✅ Consistent response format  
✅ Meaningful endpoint names  
✅ Path variables and query parameters  
✅ JSON request/response bodies  
✅ Error handling with descriptive messages  
✅ Code organization and separation of concerns  
✅ Java naming conventions  

---

## Future Enhancements

- [ ] Database integration (PostgreSQL/MySQL)
- [ ] Spring Data JPA
- [ ] Input validation (@Valid, @NotNull)
- [ ] Spring Security (JWT authentication)
- [ ] Pagination for large datasets
- [ ] Swagger/OpenAPI documentation
- [ ] Unit and integration tests
- [ ] Docker containerization
- [ ] CI/CD pipeline

---

## Student Information

**Assignment:** RESTful API Development - Bonus Question  
**Project:** User Profile API  
**Branch:** restFull_api_[StudentId]  
**Framework:** Spring Boot  

---

## Additional Resources

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/)
- [Spring Web MVC](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html)
- [RESTful API Design Best Practices](https://restfulapi.net/)
- [HTTP Status Codes](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)
- [Postman Learning Center](https://learning.postman.com/)

---

## License & Copyright

This project is created for educational purposes as part of a Spring Boot REST API assignment.

---

**Last Updated:** 2024  
**Version:** 1.0.0

---

*For detailed architecture information, see [architecture.md](architecture.md)*  
*For API testing, import [postman_collection.json](postman_collection.json)*
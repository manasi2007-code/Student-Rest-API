# Student-Rest-API
# Student Records REST API

A RESTful API built with Spring Boot to manage student records. It supports full CRUD operations, input validation, and structured error handling, and stores data in an in-memory H2 database.

Built for Experiment 7: *Develop a RESTful API using Spring Boot to manage student records*.

## Tech Stack

- Java 17+
- Spring Boot (Spring Web, Spring Data JPA, Validation)
- H2 in-memory database
- Maven

## Project Structure

```
src/main/java/com/example/studentapi
├── controller   → Studentcontroller        (REST endpoints)
├── exception    → StudentNotFoundException, GlobalExceptionHandler
├── model        → Student                  (JPA entity + validation)
├── repository   → Studentrepo              (Spring Data JPA repository)
├── service      → Studentservice           (business logic)
└── StudentapiApplication                   (entry point)
```

## Student Model

| Field | Type | Rules |
|---|---|---|
| id | Long | Auto-generated |
| rollNumber | String | Required, unique |
| firstName | String | Required |
| lastName | String | Optional |
| email | String | Valid email format, unique |
| department | String | Optional |
| year | int | 1 to 4 |
| gpa | double | 0.0 to 10.0 |

**Note:** `year` is a reserved keyword in H2 2.x, so the database column is named `study_year` (via `@Column(name = "study_year")`). The Java field and the JSON key are still `year`.

## Getting Started

### Prerequisites
- JDK 17 or newer
- Maven (or use the bundled Maven wrapper)

### Run in IntelliJ
1. Open the project folder containing `pom.xml` (File → Open) and wait for Maven to finish importing.
2. Open `StudentapiApplication.java` and click the green ▶ next to `main`.
3. Wait for `Tomcat started on port 8080` in the console.

### Run from the command line
```bash
mvn spring-boot:run
```

The API is available at `http://localhost:8080`. If port 8080 is busy, add `server.port=8081` to `application.properties`.

## API Endpoints

Base URL: `http://localhost:8080/api/students`

| Action | Method | Endpoint | Body |
|---|---|---|---|
| Create student | POST | `/api/students` | Student JSON |
| Get all students | GET | `/api/students` | none |
| Get one student | GET | `/api/students/{id}` | none |
| Update student | PUT | `/api/students/{id}` | Student JSON |
| Delete student | DELETE | `/api/students/{id}` | none |

### Sample request body
```json
{
  "rollNumber": "CS001",
  "firstName": "Asha",
  "lastName": "Kumar",
  "email": "asha@example.com",
  "department": "CS",
  "year": 2,
  "gpa": 8.5
}
```

### Sample cURL
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{"rollNumber":"CS001","firstName":"Asha","lastName":"Kumar","email":"asha@example.com","department":"CS","year":2,"gpa":8.5}'
```

On Windows, use Postman or Command Prompt with escaped quotes, since PowerShell handles `curl` differently.

## Error Handling

| Situation | Status | Example response |
|---|---|---|
| Student id not found | 404 | `{"error":"Student not found with id 999"}` |
| Validation failure (bad email, year out of range, etc.) | 400 | `{"email":"must be a well-formed email address","year":"must be less than or equal to 4"}` |
| Duplicate roll number or email | 409 | `{"error":"Roll number or email already exists"}` |

Errors are handled centrally in `GlobalExceptionHandler` using `@RestControllerAdvice`.

## Testing

Use Postman or cURL.

1. **Create:** POST the sample body. Expect `200` and `"id": 1`.
2. **Read:** GET `/api/students` and `/api/students/1`.
3. **Update:** PUT `/api/students/1` with changed values.
4. **Errors:**
   - GET `/api/students/999` → 404
   - POST with `"year": 7` and `"email": "abc"` → 400
   - POST the same roll number twice → 409
5. **Delete:** DELETE `/api/students/1`, then GET it again → 404.

## H2 Console

With the app running, open `http://localhost:8080/h2-console` and connect with:

- JDBC URL: `jdbc:h2:mem:studentdb`
- User Name: `sa`
- Password: *(blank)*

Run `SELECT * FROM STUDENT;` to view stored records.

The database is in-memory, so all data is lost when the application stops, and ids restart from 1.

## Configuration

`src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:studentdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=false
```

## Known Behaviour

- `PUT` does not change `rollNumber`; roll numbers are treated as permanent.
- The H2 console is enabled for learning purposes and should be disabled in any production setup.

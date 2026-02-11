# AddressBook REST API — Spring Boot + PostgreSQL

## Project Overview

AddressBook REST API is a layered Spring Boot application that provides RESTful CRUD operations for managing contacts. The project is built using Controller–Service–Repository–Model architecture and demonstrates DTO usage, request validation, global exception handling, structured logging, and environment-based configuration.

The service exposes endpoints to create, read, update, and delete contact records stored in a PostgreSQL database. Validation rules and user-friendly error responses are implemented to ensure robust API behavior.

This project is suitable for learning Spring Boot REST design patterns and for backend assignment or interview evaluation.

---

## Architecture

The application follows a layered architecture:

* Controller Layer — Handles HTTP requests and returns responses
* Service Layer — Contains business logic (implemented as a concrete class, no interface)
* Repository Layer — Handles database access using Spring Data JPA
* Model Layer — JPA entity mapped to database table
* DTO Layer — API request/response objects with validation rules
* Exception Layer — Global exception handling and custom exceptions

---

## Technology Stack

* Java 17+
* Spring Boot
* Spring Web
* Spring Data JPA
* PostgreSQL
* Hibernate Validator (Bean Validation)
* Lombok
* Logback Logging
* Maven

---

## Key Features

* RESTful CRUD APIs
* Clean layered architecture
* DTO-based request and response
* Bean validation for REST inputs
* Pattern validation on name field
* Validation applied to create and update APIs
* Global exception handling using RestControllerAdvice
* Custom not-found exception handling
* User-friendly validation error responses
* Lombok annotations for boilerplate reduction
* Lombok Slf4j logging integration
* Logback console and file logging
* Profile-based configuration (dev/prod)
* Environment-variable-based database configuration

---

## Project Structure

```
src/main/java/com/example/addressbook

controller/
  ContactController.java

service/
  ContactService.java

repository/
  ContactRepository.java

model/
  Contact.java

dto/
  ContactDTO.java

exception/
  AddressBookNotFoundException.java
  GlobalExceptionHandler.java
  ErrorResponse.java

AddressBookApplication.java

src/main/resources
  application.properties
  application-dev.properties
  application-prod.properties
  logback.xml
```

---

## Data Model

### Contact Entity

Represents a contact stored in the database.

Fields:

* id — Primary key, auto-generated
* name — Contact name
* email — Email address
* phone — Phone number

---

## DTO Model

### ContactDTO

Used for API input and output and contains validation rules.

Validation constraints:

* name — required, letters and spaces only
* email — must be valid email format
* phone — required field

Validation is enforced on POST and PUT endpoints using @Valid.

---

## Maven Dependencies

Required dependencies in pom.xml:

```xml
<dependencies>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
  </dependency>

  <dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
  </dependency>

  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <scope>provided</scope>
  </dependency>

</dependencies>
```

---

## Configuration Files

Configuration is profile-based and uses environment variables for database credentials. These files should be created locally and not committed with real credentials.

Create under:

```
src/main/resources
```

---

### application.properties

```properties
spring.application.name=addressbook-api
spring.profiles.active=dev

spring.jpa.open-in-view=false
spring.jpa.properties.hibernate.format_sql=true

server.port=8080
```

---

### application-dev.properties

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASS}
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

logging.level.root=INFO
logging.level.com.example.addressbook=DEBUG
```

---

### application-prod.properties

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASS}

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false

logging.level.root=WARN
logging.level.com.example.addressbook=INFO
```

---

## Environment Variables

Set database credentials as environment variables before running.

Windows:

```
setx DB_URL jdbc:postgresql://localhost:5432/addressbook_db
setx DB_USER postgres
setx DB_PASS your_password
```

Linux/macOS:

```
export DB_URL=jdbc:postgresql://localhost:5432/addressbook_db
export DB_USER=postgres
export DB_PASS=your_password
```

Restart terminal after setting variables.

---

## Logging Configuration

Logging is configured using logback.xml.

* Console logging enabled
* File logging enabled (logs/app.log)
* Pattern includes timestamp, level, logger, and message
* Logging levels configurable per profile

Lombok @Slf4j is used in controller, service, and exception handler classes.

---

## REST API Endpoints

Base URL:

```
/api/contacts
```

---

### Create Contact

POST /api/contacts

Request:

```json
{
  "name": "John Doe",
  "email": "john@test.com",
  "phone": "9999999999"
}
```

Response: 201 Created

---

### Get All Contacts

GET /api/contacts

Response: 200 OK

---

### Get Contact By ID

GET /api/contacts/{id}

Response:

* 200 OK if found
* 404 Not Found if missing

---

### Update Contact

PUT /api/contacts/{id}

Validation applied.

Response:

* 200 OK
* 404 Not Found if id does not exist

---

### Delete Contact

DELETE /api/contacts/{id}

Response:

* 204 No Content
* 404 Not Found if id does not exist

---

## Validation Behavior

If validation fails, the API returns HTTP 400 with field-level error messages.

Example response:

```json
{
  "name": "Name is required",
  "email": "Invalid email format"
}
```

---

## Exception Handling

Global exception handling is implemented using RestControllerAdvice.

Handled scenarios:

* Validation errors
* Resource not found
* Bad request input
* Unexpected server errors

Custom exception:

AddressBookNotFoundException — thrown when a contact ID is not found.

---

## Database Setup

Create database in PostgreSQL:

```sql
CREATE DATABASE addressbook_db;
```

Tables are auto-created by Hibernate when the application starts.

---

## Build and Run

Build project:

```
mvn clean install
```

Run project:

```
mvn spring-boot:run
```

or run the main class:

```
AddressBookApplication.java
```

---

## API Testing

You can test endpoints using:

* curl
* Postman
* Insomnia

Example:

```
curl http://localhost:8080/api/contacts
```

---

## Design Decisions

* DTO separates API contract from persistence model
* Service implemented as class (no interface) per requirement
* Validation placed at DTO level
* Global exception handler ensures consistent error responses
* Environment-variable configuration improves security
* Lombok reduces boilerplate
* Logback provides structured logging

---


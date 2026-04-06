# Todo API

This is a small Spring Boot REST API for managing todos.

The project is intentionally simple, intended to help get comfortable with how Java APIs are structured. It shows how requests move from a controller, into a service, through a repository, and finally into a database table using Spring Data JPA.

## What This Project Does

The API lets you:

- create a todo
- list all todos
- fetch a todo by ID
- update a todo
- delete a todo

It also includes:

- an in-memory H2 database
- basic error handling for missing todos
- integration tests using `MockMvc`

## Tech Stack

- Java 17
- Maven
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database
- JUnit 5

## Project Structure

Here is the main idea behind the package layout:

- `controller`: receives HTTP requests and returns HTTP responses
- `service`: contains the application logic
- `repository`: talks to the database through Spring Data JPA
- `entity`: database-mapped classes
- `model`: API response model
- `dto`: request payload classes
- `exception`: custom exceptions and API error handling

If you're new to this pattern, this is the flow for a typical request:

`HTTP request -> Controller -> Service -> Repository -> Database`

And on the way back:

`Database -> Repository -> Service -> Controller -> JSON response`

## How The Data Types Work

This project uses a few different classes for a reason.

### `TodoEntity`

[`TodoEntity`](src/main/java/com/githubjon_steele/todoapi/entity/TodoEntity.java) is the class JPA maps to the database table. It uses annotations like `@Entity`, `@Table`, and `@Id`, so Hibernate knows how to store and read todo records.

This is the database-facing class.

### `Todo`

[`Todo`](src/main/java/com/githubjon_steele/todoapi/model/Todo.java) is the model returned by the API. It is not a JPA entity. In this project, the service converts `TodoEntity` into `Todo` before sending it back to the controller.

This is the response-facing class.

### DTOs

[`CreateTodoRequest`](src/main/java/com/githubjon_steele/todoapi/dto/CreateTodoRequest.java) and [`UpdateTodoRequest`](src/main/java/com/githubjon_steele/todoapi/dto/UpdateTodoRequest.java) are DTOs, which stands for Data Transfer Objects.

They represent the JSON the client sends into the API.

For example:

```json
{
  "title": "Write README",
  "description": "Explain the project clearly"
}
```

That JSON can be turned into a `CreateTodoRequest` by Spring automatically.

In short:

- entity = database shape
- dto = incoming request shape
- model = outgoing response shape

## Endpoints

### Health Check

- `GET /`

Returns:

```text
Todo API is running
```

### Get All Todos

- `GET /api/todos`

### Get Todo By ID

- `GET /api/todos/{id}`

### Create Todo

- `POST /api/todos`

Example request body:

```json
{
  "title": "Write API",
  "description": "Create the first todo endpoint"
}
```

### Update Todo

- `PUT /api/todos/{id}`

Example request body:

```json
{
  "title": "New title",
  "description": "Updated description",
  "completed": true
}
```

### Delete Todo

- `DELETE /api/todos/{id}`

## Error Handling

If a todo does not exist, the service throws [`TodoNotFoundException`](src/main/java/com/githubjon_steele/todoapi/exception/TodoNotFoundException.java).

That exception is caught by [`ApiExceptionHandler`](src/main/java/com/githubjon_steele/todoapi/exception/ApiExceptionHandler.java), which returns a JSON error response using [`ApiError`](src/main/java/com/githubjon_steele/todoapi/exception/ApiError.java).

Example:

```json
{
  "status": 404,
  "error": "Todo with id 99 was not found"
}
```

## Running The App

From the project root:

```bash
./mvnw spring-boot:run
```

Once the app is running, it will usually be available at:

```text
http://localhost:8080
```

You can test that quickly by visiting:

```text
http://localhost:8080/
```

## Running Tests

```bash
./mvnw test
```

The test suite checks the main API behavior, including:

- the home endpoint
- creating a todo
- returning `404` for a missing todo
- updating a todo
- deleting a todo

## H2 Database

This app uses an in-memory H2 database, which means the data is temporary and resets when the application stops.

The H2 console is enabled in `application.properties` and is available at:

```text
http://localhost:8080/h2-console
```

The configured datasource URL is:

```text
jdbc:h2:mem:todoapi
```

That makes this project convenient for learning and experimenting, because there is no separate database setup required.

## Why This Project Is Useful

This codebase is a nice example of a clean beginner-friendly Spring Boot API because it shows:

- how REST endpoints are built
- how data moves through layered architecture
- how JPA entities differ from DTOs and response models
- how exception handling can be centralized
- how to test API behavior with Spring Boot tests

## Next Ideas

If you want to keep growing the project, some natural next steps would be:

- add request validation with `@Valid`
- add a real database like PostgreSQL
- split tests into controller and service tests
- add created/updated timestamps
- add filtering, sorting, or search
- document the API with Swagger / OpenAPI

## Final Note

This is a simple project, but that is part of its value. It is small enough to understand end-to-end, and that makes it a strong practice project for learning Spring Boot without getting buried in framework complexity.

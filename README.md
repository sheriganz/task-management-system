# Task Management System

A REST backend for a simple task tracker, built with Spring Boot as the graduation project of the
Hyperskill track "Java Backend Developer (Spring Boot)".

Users register, obtain a JWT, create tasks, assign them to other users, change their status and
leave comments.

## Tech stack

- Java 21, Spring Boot 3.5
- Spring Web, Spring Validation
- Spring Security: HTTP Basic for login, OAuth2 Resource Server (RSA-signed JWT) for everything else
- Spring Data JPA / Hibernate, H2 (file database)
- Gradle

## Running

```bash
./gradlew bootRun
```

The service starts on `http://localhost:8080` and stores data in `./data/tms_db.mv.db`.

## API

All endpoints except registration require `Authorization: Bearer <token>`.

| Method | Path | Description |
|---|---|---|
| `POST` | `/api/accounts` | Register a user |
| `POST` | `/api/auth/token` | Get a JWT (HTTP Basic auth) |
| `POST` | `/api/tasks` | Create a task |
| `GET` | `/api/tasks?author=&assignee=` | List tasks, newest first, optional filters |
| `PUT` | `/api/tasks/{id}/assign` | Assign a task (author only) |
| `PUT` | `/api/tasks/{id}/status` | Change status (author or assignee) |
| `POST` | `/api/tasks/{id}/comments` | Add a comment |
| `GET` | `/api/tasks/{id}/comments` | List comments, newest first |

### Register

```http
POST /api/accounts
{"email": "user@mail.com", "password": "secret1"}
```

`200 OK`; `400` for an invalid email or a password shorter than 6 characters; `409` if the email
is taken (case-insensitive).

### Get a token

```bash
curl -X POST -u user@mail.com:secret1 http://localhost:8080/api/auth/token
```

```json
{"token": "eyJhbGciOiJSUzI1NiJ9..."}
```

The token is valid for one hour. Signing keys are generated at startup, so tokens do not survive a
restart.

### Tasks

```http
POST /api/tasks
{"title": "Write README", "description": "Describe the API"}
```

```json
{"id": "1", "title": "Write README", "description": "Describe the API",
 "status": "CREATED", "author": "user@mail.com", "assignee": "none"}
```

- `PUT /api/tasks/{id}/assign` with `{"assignee": "other@mail.com"}` or `{"assignee": "none"}`.
  `403` if the caller is not the author, `404` if the task or user does not exist.
- `PUT /api/tasks/{id}/status` with `{"status": "IN_PROGRESS"}`. Allowed values: `CREATED`,
  `IN_PROGRESS`, `COMPLETED`. `403` if the caller is neither the author nor the assignee.
- `GET /api/tasks` additionally returns `total_comments` for every task. Comment counts are loaded
  with a single `GROUP BY` query for the whole page instead of one query per task.

### Comments

```http
POST /api/tasks/1/comments
{"text": "Looks good"}
```

```http
GET /api/tasks/1/comments
```

```json
[{"id": "1", "task_id": "1", "text": "Looks good", "author": "user@mail.com"}]
```

`400` for blank text, `404` if the task does not exist.

## Notes

This is a learning project. `/actuator/shutdown` and the H2 console are open because the course
tests rely on them; they should be closed in any real deployment.

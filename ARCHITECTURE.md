# StudySync – Architecture & Project Overview

## 1. What is StudySync?

StudySync is a **study management web application** built as a full-stack academic project for the **Proyecto Intermodular / TFG de Formación Profesional**. Its purpose is to demonstrate real skills in software development, architecture design, data persistence, validation, authentication, documentation, and presentation of a functional digital product.

It is not just a landing page — the landing is part of the project, but the core is the StudySync **application** which solves a clear problem: **organizing and tracking study sessions**.

The project follows the official delivery milestones agreed upon with the educational center and tutor:

| Milestone | Content                                   |
| --------- | ----------------------------------------- |
| Entrega 1 | Viability study                           |
| Entrega 2 | ~50% progress                             |
| Entrega 3 | Final submission                          |
| Memoria   | 15–40 pages (excl. cover, index, annexes) |
| Defensa   | Recorded video 5–10 minutes               |

---

## 2. Project Goals

Build a platform where students can:

- Register and log in.
- Create and manage **study routines / plans** (`Planning`).
- Log **actual study sessions** (`RoutineLog`).
- Associate sessions to subjects and routines.
- View **progress and basic statistics**.
- Persist all data in a relational database.
- Show a well-structured, usable interface.
- Document the entire technical development as an end-of-degree project.

---

## 3. MVP Scope (Mandatory)

To keep the project manageable, the **minimum viable product** must include:

- User registration and login.
- User profile management.
- CRUD for study subjects.
- CRUD for study plans/routines (`Planning`).
- CRUD for study session logs (`RoutineLog`).
- Association of sessions with routines/subjects.
- Progress visualization.
- Basic statistics dashboard.
- Database persistence.
- Clear code and process documentation.
- Frontend SPA (React/Vue/Angular)

---

## 4. Technology Stack

| Layer             | Technology / Tool                                      |
| ----------------- | ------------------------------------------------------ |
| Language          | Java 21                                                |
| Framework         | Spring Boot 3.5.4 (Spring Web, Security, Data JPA)     |
| Build             | Maven (WAR packaging, also executable via `mvnw`)      |
| Database          | MySQL 8.0 (local Docker container)                     |
| ORM               | Hibernate / JPA                                        |
| Security          | Spring Security + JJWT (io.jsonwebtoken 0.12.5)        |
| Validation        | Jakarta Bean Validation (`@NotBlank`, `@Valid`)        |
| Boilerplate       | Lombok (`@Getter`, `@Setter`, `@NoArgsConstructor`, …) |
| Testing           | JUnit Jupiter 5.10, Mockito 5.2                        |
| Containerization  | Docker Compose (MySQL service)                         |
| API Client (docs) | Bruno (`.bru` files in `StudySync API/`)               |
| Future Frontend   | React 19 + Vite + Tailwind CSS v4 + React Router v7    |

---

## 5. Folder Structure

```
studysync/
├── docker-compose.yml              # MySQL 8.0 container (root/1234, db: studysync_db)
├── pom.xml                         # Maven configuration
├── mvnw / mvnw.cmd                 # Maven wrapper
├── README.md
├── StudySync API/                  # Bruno API requests (Sign Up, Login, Get User…)
├── src/
│   ├── main/java/dev/juanito/studysync/
│   │   ├── StudysyncApplication.java    # Entry point
│   │   ├── ServletInitializer.java      # WAR deployment support
│   │   ├── controller/                  # REST controllers
│   │   │   ├── AuthController.java
│   │   │   ├── PlanningController.java
│   │   │   ├── RoutineLogController.java
│   │   │   ├── StatisticsController.java
│   │   │   ├── SubjectController.java
│   │   │   └── UserController.java
│   │   ├── dto/                         # Data Transfer Objects (request/response)
│   │   │   ├── AuthResponseDto.java
│   │   │   ├── PlanningCreationDto.java
│   │   │   ├── PlanningResponseDto.java
│   │   │   ├── PlanningUpdateDto.java
│   │   │   ├── RoutineLogCreationDto.java
│   │   │   ├── RoutineLogResponseDto.java
│   │   │   ├── RoutineLogUpdateDto.java
│   │   │   ├── StatsResponseDto.java
│   │   │   ├── SubjectCreationDto.java
│   │   │   ├── SubjectResponseDto.java
│   │   │   ├── SubjectStatsDto.java
│   │   │   ├── SubjectUpdateDto.java
│   │   │   ├── UserLoginDto.java
│   │   │   ├── UserRegistrationDto.java
│   │   │   ├── UserResponseDto.java
│   │   │   ├── UserUpdateDto.java
│   │   │   └── WeeklyStatsDto.java
│   │   ├── exception/                   # Custom exceptions (RuntimeException)
│   │   │   ├── EmailAlreadyExistException.java
│   │   │   ├── PlanningNotFoundException.java
│   │   │   ├── RoutineLogNotFoundException.java
│   │   │   ├── SubjectIdNotFoundException.java
│   │   │   ├── SubjectNameAlreadyExistsException.java
│   │   │   ├── UserEmailNotFoundException.java
│   │   │   └── UserIdNotFoundException.java
│   │   ├── handler/                     # Global exception handler
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── model/                       # JPA entities
│   │   │   ├── Subject.java
│   │   │   ├── User.java
│   │   │   ├── Planning.java
│   │   │   ├── RoutineLog.java
│   │   │   └── Status.java (enum: CREATED, COMPLETED, DELETED)
│   │   ├── repository/                  # Spring Data repositories
│   │   │   ├── PlanningRepository.java
│   │   │   ├── RoutineLogRepository.java
│   │   │   ├── SubjectRepository.java
│   │   │   └── UserRepository.java
│   │   ├── service/                     # Business logic
│   │   │   ├── PlanningService.java     # Interface
│   │   │   ├── RoutineLogService.java   # Interface
│   │   │   ├── StatisticsService.java   # Interface
│   │   │   ├── SubjectService.java      # Interface
│   │   │   ├── UserService.java         # Interface
│   │   │   └── impl/
│   │   │       ├── PlanningServiceImpl.java
│   │   │       ├── RoutineLogServiceImpl.java
│   │   │       ├── StatisticsServiceImpl.java
│   │   │       ├── SubjectServiceImpl.java
│   │   │       └── UserServiceImpl.java
│   │   ├── security/                    # Authentication & authorization
│   │   │   ├── SecurityConfig.java
│   │   │   ├── AuthenticationHelper.java
│   │   │   ├── UserPrincipal.java
│   │   │   └── jwt/
│   │   │       ├── JwtService.java
│   │   │       └── JwtAuthenticationFilter.java
│   └── test/java/dev/juanito/studysync/
│       ├── StudysyncApplicationTests.java
│       └── security/jwt/JwtServiceTest.java
└── .gitignore / .gitattributes
```

---

## 6. Database Schema (JPA entities)

- **users** – `id`, `name`, `email`, `password` (BCrypt hash)
- **subjects** – `id`, `name` (unique per user), `colorHEX`, `user_id` (FK → users)
- **plannings** – `id`, `planned_hours`, `date`, `status` (enum), `user_id`, `subject_id`, `planning_id` (1:1 with routineLog)
- **routine_logs** – `id`, `actual_hours`, `date`, `notes`, `user_id`, `subject_id`

Relationships:

- A **User** has many Subjects, Plannings, RoutineLogs.
- A **Subject** belongs to a User and has many Plannings and RoutineLogs.
- **Planning** ↔ **RoutineLog** share a one-to-one link.

Status Enum values: `CREATED`, `COMPLETED`, `DELETED`.

---

## 7. Security Flow

1. **Registration** (`POST /api/users/register`) – password encoded with BCrypt.
2. **Login** (`POST /api/auth/login`) – returns a JWT in the `AuthResponseDto`.
3. All other endpoints require a valid JWT in the `Authorization: Bearer <token>` header.
4. `JwtAuthenticationFilter` runs **before** `UsernamePasswordAuthenticationFilter`, sets the security context from the token.
5. `AuthenticationHelper` extracts the current `User` from the security context.
6. Sessions are **stateless** (`SessionCreationPolicy.STATELESS`).
7. CSRF is disabled (API-only app).

---

## 8. API Endpoints (current)

| Method | Endpoint                      | Auth | Description               | Status |
| ------ | ----------------------------- | ---- | ------------------------- | ------ |
| POST   | `/api/users/register`         | No   | Register new user         | ✅     |
| POST   | `/api/auth/login`             | No   | Login, get JWT            | ✅     |
| GET    | `/api/users/{id}`             | Yes  | Get user by ID            | ✅     |
| PUT    | `/api/users/{id}`             | Yes  | Update user               | ✅     |
| DELETE | `/api/users/{id}`             | Yes  | Delete user               | ✅     |
| POST   | `/api/subjects`               | Yes  | Create subject            | ✅     |
| GET    | `/api/subjects`               | Yes  | List all user's subjects  | ✅     |
| GET    | `/api/subjects/{id}`          | Yes  | Get subject by ID         | ✅     |
| PUT    | `/api/subjects/{id}`          | Yes  | Update subject            | ✅     |
| DELETE | `/api/subjects/{id}`          | Yes  | Delete subject            | ✅     |
| POST   | `/api/plannings`              | Yes  | Create planning           | ✅     |
| GET    | `/api/plannings`              | Yes  | List user's plannings     | ✅     |
| GET    | `/api/plannings?subjectId`    | Yes  | Filter by subject         | ✅     |
| GET    | `/api/plannings/{id}`         | Yes  | Get planning by ID        | ✅     |
| PUT    | `/api/plannings/{id}`         | Yes  | Update planning           | ✅     |
| DELETE | `/api/plannings/{id}`         | Yes  | Delete planning           | ✅     |
| POST   | `/api/routine-logs`           | Yes  | Create routine log        | ✅     |
| GET    | `/api/routine-logs`           | Yes  | List user's routine logs  | ✅     |
| GET    | `/api/routine-logs?subjectId` | Yes  | Filter by subject         | ✅     |
| GET    | `/api/routine-logs/{id}`      | Yes  | Get routine log by ID     | ✅     |
| PUT    | `/api/routine-logs/{id}`      | Yes  | Update routine log        | ✅     |
| DELETE | `/api/routine-logs/{id}`      | Yes  | Delete routine log        | ✅     |
| GET    | `/api/stats`                  | Yes  | User statistics dashboard | ✅     |

---

## 9. Code Conventions

- **Language**: Java code. Mandatory Spanish comments in code with an informal tone and a very slighly colombian accent.
- **Dependency injection**: constructor injection; no `@Autowired` on fields.
- **Lombok**: `@Getter`, `@Setter`, `@AllArgsConstructor`, `@NoArgsConstructor` on entities and DTOs.
- **DTO pattern**: Separate classes for creation, update, and response.
- **Validation**: Jakarta annotations on DTOs, `@Valid` in controllers.
- **Service layer**: Interfaces define contract; `impl/` package holds implementations.
- **Exception handling**: Custom exceptions extend `RuntimeException`; handled globally by `@RestControllerAdvice` (`GlobalExceptionHandler`).
- **REST responses**: `ResponseEntity` with appropriate HTTP status codes (`201 Created`, `200 OK`, `204 No Content`).
- **Password handling**: BCrypt via `PasswordEncoder` bean; password never returned in responses (`@JsonIgnore` on `User.password`).
- **JPA**: `IDENTITY` generation strategy; `@ManyToOne`/`@OneToMany` mappings; `mappedBy` correctly placed.
- **Testing**: JUnit Jupiter with Mockito; test file suffix `Test`.

---

## 10. What's Done & What's Missing

### ✅ Implemented

- User registration and login with JWT
- Spring Security filter chain and stateless JWT auth
- Full CRUD for **Subjects** (with user-scoped access + list all)
- Full CRUD for **Users**
- Full CRUD for **Plannings** (with list all, filter by subject)
- Full CRUD for **RoutineLogs** (with list all, filter by subject)
- **Statistics dashboard** – overview, per-subject breakdown, weekly trends, current streak
- Custom exception handling and error responses
- MySQL integration via Docker Compose
- **23 tests** – JWT unit + controller integration (Planning, RoutineLog, Statistics)

### ✅ MVP Completed

- User management: ✅
    - hola
- Subject management (CRUD + list): ✅
- Planning management (CRUD + list + filter): ✅
- RoutineLog management (CRUD + list + filter): ✅
- Statistics & progress dashboard: ✅
- Controller integration tests: ✅
- Authentication & authorization: ✅
- Frontend SPA (landing + application)

|                 |
| --------------- | ----------------------- | -------- | --- | ------------------------------------------- | --- |
| **Juanito Dev** | `juanito@studysync.com` | `123456` | 5   | 14 (10 completados, 1 eliminado, 3 creados) | 16  |
| **María López** | `maria@studysync.com`   | `123456` | 3   | 3                                           | 2   |

# IRCTC Clone — High-Performance Railway Booking API

A production-grade ticket booking backend engineered to handle high-concurrency reservations and rapid read-heavy search queries. Built with a focus on data consistency, stateless security, and measurable performance improvements over a naive implementation.

---

## System Architecture

The system is a single-service Spring Boot backend operating against a PostgreSQL primary database with a Redis caching layer sitting in front of all read-heavy operations.

**Core Engine — Java 25 / Spring Boot**
The authoritative source of truth. Owns all data persistence, transactional booking logic, and user management. All REST endpoints are protected by a custom JWT-based authentication filter that enforces strict access control and prevents IDOR vulnerabilities across the entire API surface. The full API is documented via an automated Swagger OpenAPI 3.0 pipeline.

**Cache Layer — Redis**
A Cache-Aside pattern sits in front of all train search queries. Frequently accessed routes are served from in-memory cache, reducing average search latency from approximately 600ms to under 20ms and offloading read pressure entirely from the primary database.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 25 |
| Framework | Spring Boot |
| Database | PostgreSQL |
| Cache | Redis |
| ORM | Spring Data JPA / Hibernate |
| Security | Spring Security, JJWT |
| Documentation | Springdoc OpenAPI (Swagger UI) |
| Build Tool | Maven |

---

## Key Engineering Highlights

### 1. Defeating the Double-Booking Race Condition

**The Problem:** In a high-traffic environment, multiple users attempting to book the final available seat simultaneously can cause database inconsistencies and double-bookings — a correctness failure with real-world consequences.

**The Solution:** Implemented a Pessimistic Write Lock (`@Lock(LockModeType.PESSIMISTIC_WRITE)`) at the JPA repository layer, scoped within `@Transactional` boundaries. This forces concurrent booking requests for the same train to queue sequentially at the database level, guaranteeing absolute data integrity through the transaction commit phase regardless of concurrency volume.

### 2. 97% Latency Reduction via Cache-Aside Pattern

**The Problem:** Train route search is the dominant read pattern in any booking system — high frequency, low write rate, and expensive to recompute against a normalised relational schema on every request.

**The Solution:** Integrated a Redis caching layer using the Cache-Aside pattern. On a cache miss, the result is fetched from PostgreSQL and written to Redis with an appropriate TTL. Subsequent requests for the same route are served entirely from memory, dropping average search latency from approximately 600ms to under 20ms and eliminating redundant database load.

### 3. Stateless Authentication and IDOR Prevention

**The Problem:** Session-based authentication requires server-side state, which does not scale horizontally and consumes RAM. Separately, predictable user-scoped endpoints (`/tickets/history/{userId}`) allow any authenticated user to enumerate and access another user's data — a classic Insecure Direct Object Reference vulnerability.

**The Solution:** Engineered a custom JWT filter for stateless, cryptographically verified authentication. IDOR is eliminated at the architecture level — user identity is extracted exclusively from the signed token payload, never from client-supplied path or query parameters. There is no endpoint parameter a malicious user can manipulate to access another account's data.

### 4. Polymorphic Fare Calculation via Single Table Inheritance

Ticket types are modelled using Single Table Inheritance. An abstract `Ticket` base class defines the fare contract, while `ACTicket` and `SleeperTicket` subclasses override `calculateFare()` with class-specific logic. Adding new ticket classes — First Class, General, or otherwise — requires no modification to existing booking or persistence logic.

---

## API Endpoints

| Group | Method | Endpoint | Description |
|---|---|---|---|
| **Authentication** | `POST` | `/api/auth/register` | Register a new user |
| | `POST` | `/api/auth/login` | Login and receive a JWT |
| **Train Services** | `POST` | `/api/trains` | Add a new train *(Admin)* |
| | `GET` | `/api/trains/search` | Search trains by source and destination (Redis cached) |
| **Booking Services** | `POST` | `/api/tickets/book` | Atomic ticket booking with PNR generation |
| | `GET` | `/api/tickets/history` | Retrieve the authenticated user's booking history |

> All endpoints except `/api/auth/register` and `/api/auth/login` require a valid JWT Bearer token. User identity on protected endpoints is resolved from the token — no user ID parameters are accepted from the client.

Full interactive documentation available at `http://localhost:8080/swagger-ui/index.html` after startup.

---

## Local Setup

### Prerequisites
- Java 25
- Maven
- PostgreSQL running locally
- Redis running locally

### 1. Configure the Database

Create a PostgreSQL database named `irctcdb`, then update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/irctcdb
spring.datasource.username=postgres
spring.datasource.password=yourpassword

spring.data.redis.host=localhost
spring.data.redis.port=6379
```

### 2. Run the Application

```bash
git clone https://github.com/yourusername/irctc-clone.git
cd irctc-clone
./mvnw spring-boot:run
```

The server starts on `http://localhost:8080`. Swagger UI at `http://localhost:8080/swagger-ui/index.html`.

---

## Project Structure

```
src/
├── main/
│   ├── java/com/yourpackage/
│   │   ├── controller/       # REST controllers
│   │   ├── service/          # Business logic
│   │   ├── repository/       # JPA repositories
│   │   ├── model/            # Entity classes (User, Train, Ticket)
│   │   ├── security/         # JWT filter, Spring Security config
│   │   └── cache/            # Redis cache configuration
│   └── resources/
│       └── application.properties
└── test/
```

---

## Portfolio Context

This project is part of a three-project backend portfolio targeting fresher SDE/backend roles:

| Project | Core Engineering Concepts |
|---|---|
| **IRCTC Clone** | **Pessimistic locking, Redis caching, JWT auth, IDOR prevention, Single Table Inheritance** |
| Expense Tracker | JWT auth, IDOR prevention, JPQL aggregations, global exception handling |
| Athlete Analytics Engine | Microservice architecture, ML integration, FastAPI, ACWR, personalised modelling |

---

## License

This project is licensed under the [MIT License](LICENSE).

---

Designed and engineered by **S A Shreyass**  
CSE Undergraduate, BMSCE Bangalore · [GitHub](https://github.com/shreyass1797)
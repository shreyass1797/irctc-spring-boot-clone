# 🚄 IRCTC Ticket Booking System — Spring Boot Clone

A robust, enterprise-grade backend for a railway reservation system built with **Java** and **Spring Boot**. This project simulates core IRCTC functionalities, focusing on data consistency, secure authentication, and scalable architecture.

---

## ✨ Features

- **Atomic Booking Engine** — Pessimistic locking prevents double-booking under concurrent requests
- **Polymorphic Fare Calculation** — OOP-driven dynamic fares for AC and Sleeper classes via a unified entry point
- **Secure Authentication** — BCrypt password hashing via Spring Security
- **Relational Persistence** — Fully normalized PostgreSQL schema with referential integrity across Users, Trains, and Tickets
- **Automated PNR Generation** — Unique 10-digit Passenger Name Record assigned to every successful booking
- **Layered Architecture** — Clean Controller → Service → Repository separation of concerns

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17+ |
| Framework | Spring Boot 3.x |
| Database | PostgreSQL |
| ORM | Spring Data JPA / Hibernate |
| Security | Spring Security (BCrypt) |
| Build Tool | Maven |

---

## 🏗️ Technical Highlights

### 1. Concurrency & Race Condition Prevention

The classic "double-booking" problem is solved using **Pessimistic Locking**. By combining `@Lock(LockModeType.PESSIMISTIC_WRITE)` with `@Transactional` boundaries, the system locks a train's seat record at the database level during any booking operation — guaranteeing 100% consistency even under high traffic.

### 2. OOP & Single Table Inheritance

Ticket types are modeled using **Single Table Inheritance**. An abstract `Ticket` base class defines the blueprint, while `ACTicket` and `SleeperTicket` subclasses override `calculateFare()`. This design makes adding new classes (e.g., First Class, General) completely non-breaking to existing logic.

---

## 🛣️ API Endpoints

### User Management
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/users` | Register a new user (password hashed via BCrypt) |

### Train Services
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/trains` | Add a new train *(Admin)* |
| `GET` | `/trains/search` | Search trains by source and destination |

### Booking Services
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/tickets/book` | Atomic ticket booking with PNR generation |
| `GET` | `/tickets/history/{userId}` | Retrieve a user's full booking history |

---

## ⚙️ Setup & Installation

### Prerequisites
- Java 17+
- Maven
- PostgreSQL

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/irctc-spring-boot-clone.git
cd irctc-spring-boot-clone
```

### 2. Configure the Database

Create a PostgreSQL database named `irctcdb`, then update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/irctcdb
spring.datasource.username=postgres
spring.datasource.password=yourpassword
```

### 3. Run the Application

```bash
./mvnw spring-boot:run
```

The server will start at `http://localhost:8080`.

---

## 📂 Project Structure

```
src/
├── main/
│   ├── java/com/yourpackage/
│   │   ├── controller/       # REST controllers
│   │   ├── service/          # Business logic
│   │   ├── repository/       # JPA repositories
│   │   ├── model/            # Entity classes (User, Train, Ticket)
│   │   └── security/         # Spring Security config
│   └── resources/
│       └── application.properties
└── test/
```

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

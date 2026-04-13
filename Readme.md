IRCTC Ticket Booking System Clone 🚄

A robust, enterprise-grade backend for a railway reservation system built with Java and Spring Boot. This project simulates core IRCTC functionalities, focusing on data consistency, secure authentication, and scalable architecture.
🚀 Key Features

    Atomic Booking Engine: Implements strict concurrency control to prevent double-booking of seats during simultaneous requests.

    Polymorphic Fare Calculation: Utilizes OOP principles to calculate dynamic fares for different seat classes (AC, Sleeper) through a single entry point.

    Secure Authentication: User credentials are protected using Spring Security and BCrypt password hashing.

    Relational Persistence: A fully normalized PostgreSQL schema managing Users, Trains, and Tickets with referential integrity.

    Automated PNR Generation: Unique 10-digit Passenger Name Record (PNR) generation for every successful transaction.

    Layered Architecture: Follows the Controller-Service-Repository pattern for clean separation of concerns and maintainability.

🛠️ Tech Stack

    Language: Java 17+

    Framework: Spring Boot 3.x

    Database: PostgreSQL

    ORM: Spring Data JPA / Hibernate

    Security: Spring Security (BCrypt)

    Build Tool: Maven

🏗️ Technical Highlights
1. Concurrency & Race Condition Resolution

To solve the "double-booking" problem common in high-traffic reservation systems, I implemented Pessimistic Locking. By using @Lock(LockModeType.PESSIMISTIC_WRITE) and @Transactional boundaries, the system ensures that a train's seat count is locked at the database level during the booking process, guaranteeing 100% data consistency.
2. OOP & Design Patterns

The system leverages Single Table Inheritance to model different ticket types. An abstract Ticket base class defines the blueprint, while subclasses like ACTicket and SleeperTicket override the calculateFare() method. This makes the system highly extensible for adding new seat classes (e.g., First Class, General) without modifying existing business logic.
Getty Images
Explore
🛣️ API Endpoints
User Management

    POST /users - Register a new user (Passwords hashed via BCrypt).

Train Services

    POST /trains - Add a new train (Admin).

    GET /trains/search - Search trains by source and destination.

Booking Services

    POST /tickets/book - Atomic ticket booking with PNR generation.

    GET /tickets/history/{userId} - Retrieve a user's booking ledger.

⚙️ Setup Instructions

    Clone the repo:
    Bash

    git clone https://github.com/yourusername/irctc-spring-boot-clone.git

    Configure PostgreSQL:
    Update src/main/resources/application.properties with your database credentials:
    Properties

    spring.datasource.url=jdbc:postgresql://localhost:5432/irctcdb
    spring.datasource.username=postgres
    spring.datasource.password=yourpassword

    Run the application:
    Bash

    ./mvnw spring-boot:run

    Note: This project was developed as a deep-dive into backend engineering principles, specifically focusing on handling concurrent transactions and relational data modeling in a Java environment.
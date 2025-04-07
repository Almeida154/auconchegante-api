# AuConchegante 🏠🐾

A pet-friendly hosting platform connecting pet owners with trusted hosts in a
homely environment.

## About The Project

AuConchegante is a platform that facilitates connections between pet owners
needing temporary accommodation for their pets and hosts offering a
family-friendly, welcoming environment.

### Key Features

- 🔐 Secure Authentication (JWT + OAuth2)
- 📍 Host Geolocation
- 💬 Real-time Chat
- 📅 Booking System
- 💳 Integrated Payments
- ⭐ Rating System
- 📧 Email Notifications
- 📲 Push Notifications (for auconchegante-app)

## Tech Stack

### Backend

```yaml
Core:
  - Java 21
  - Spring Boot 3.x
  - Spring Security
  - Spring Data JPA
  - Spring WebSocket

Database:
  - PostgreSQL
  - Redis (caching)
  - MongoDB (messaging)

Cloud:
  - AWS S3 (image storage)
  - AWS EC2

Messaging:
  - Apache Kafka

Documentation:
  - OpenAPI
```

### Entity Relationship Diagram

![ERD](/erd.png)

### Architecture

- Ports and Adapters Architecture
- Domain-Driven Design
- REST APIs
- WebSocket for real-time communication

## Prerequisites

- Java 21+
- Docker
- Maven
- PostgreSQL
- Redis

## Getting Started

```bash
# Clone the repository
git clone https://github.com/almeida154/auconchegante-api.git

# Navigate to directory
cd auconchegante-api

# Install dependencies
mvn install

# Set up environment variables
cp .env.example .env

# Start containers
docker-compose up -d

# Run migrations
mvn flyway:migrate

# Run the project
mvn spring-boot:run
```

## Project Structure

```
auconchegante-api/
├── src/main/java/com/auconchegante-api/
│   ├── domain/                  # Domain Layer (inside hexagon)
│   │   ├── model/               # Domain entities
│   │   ├── service/             # Domain services
│   │   └── port/                # Ports (interfaces)
│   │       ├── incoming/        # Input ports (use cases)
│   │       └── outgoing/        # Output ports
│   │
│   ├── application/             # Application Layer
│   │   └── service/             # Application services
│   │
│   └── infrastructure/          # Infrastructure Layer (outside hexagon)
│       ├── persistence/         # Database adapters
│       │   ├── entity/          # JPA entities
│       │   ├── repository/      # Spring repositories
│       │   └── mapper/          # Entity mappers
│       ├── security/            # Security config
│       └── web/                 # Web adapters
│           ├── controller/      # REST controllers
│           └── dto/             # DTOs
```

### Commit Standards

```
feat: new feature
fix: bug fix
docs: documentation
style: formatting
refactor: code refactoring
test: testing
chore: maintenance

For example:
feat(auth): add jwt authentication
```

---

With ❤️ by [David](https://github.com/almeida154) - davidalmeida154of@gmail.com

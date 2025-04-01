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

# Run the project
./mvnw spring-boot:run
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

## Contributing

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Commit Standards

```
feat: new feature
fix: bug fix
docs: documentation
style: formatting
refactor: code refactoring
test: testing
chore: maintenance
```

## Environment Variables

```env.example
# Server
SERVER_PORT=8080

# Database
DB_URL=jdbc:postgresql://localhost:5432/auconchegante
DB_USERNAME=postgres
DB_PASSWORD=postgres

# JWT
JWT_SECRET=your-secret-key
JWT_EXPIRATION=86400000

# AWS
AWS_ACCESS_KEY=your-access-key
AWS_SECRET_KEY=your-secret-key
AWS_REGION=us-east-1
S3_BUCKET=auconchegante-files
```

---

With ❤️ by [David](https://github.com/almeida154) - davidalmeida154of@gmail.com

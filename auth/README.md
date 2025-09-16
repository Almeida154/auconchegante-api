### Project Structure

auth-api/
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

### Set up environment variables

```bash
# Copy the application file with envs
cp src/main/resources/application.yaml.example src/main/resources/application.yaml

# Install dependencies
mvn install

# Run the project
mvn spring-boot:run
```

```bash
# Copy the flyway file with envs to run migrations
cp src/main/resources/db/flyway.properties.example src/main/resources/db/flyway.properties

# Run migrations
mvn flyway:migrate
```

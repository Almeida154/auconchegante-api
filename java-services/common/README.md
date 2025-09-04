### Set up environment variables

```bash
# Copy the application file with envs
cp src/main/resources/db/flyway.properties.example src/main/resources/db/flyway.properties

# Install dependencies
mvn install

# Run migrations
mvn flyway:migrate
```

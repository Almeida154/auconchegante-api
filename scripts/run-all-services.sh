cd java-services/auth
mvn spring-boot:run
AUTH_PID=$!
cd ../..

echo "Services running on PIDs: AUTH - $AUTH_PID"
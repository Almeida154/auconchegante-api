### Design, Architecture & Core features

- ✅ Hexagonal architecture structure
- ✅ Containerization with Docker
- ✅ Error handling
- ✅ Swagger configuration
- ✅ Unit tests setup
- ✅ Integration tests setup
- [ ] Logger

### Auth service STORIES

- [ ] <b>Sign in endpoint</b>

  - ✅ Users table migration
  - ✅ User domain model and entity
  - ✅ User object mapper
  - ✅ User repository
  - ✅ Token generator / validator
  - ✅ Use case
  - ✅ Controller & DTOs
  - [ ] Google OAuth
  - ✅ Swagger documentation
  - ✅ Unit tests
  - ✅ Integration tests

- [ ] <b>Sign up endpoint</b>

  - ✅ Password encrypter / decrypter
  - ✅ Use case
  - ✅ Controller & DTOs
  - [ ] Google OAuth
  - ✅ Swagger documentation
  - ✅ Unit tests
  - ✅ Integration tests

- ✅ <b>Forgot password endpoint</b>

  - ✅ PasswordResetCodes migration
  - ✅ PasswordResetCode domain model and entity
  - ✅ PasswordResetCode object mapper
  - ✅ PasswordResetCode repository
  - ✅ Code generator
  - ✅ Email delivery
  - ✅ Use case
  - ✅ Controller & DTOs
  - ✅ Swagger documentation
  - ✅ Unit tests
  - ✅ Integration tests

- ✅ <b>Validate password reset code endpoint</b>

  - ✅ Use case
  - ✅ Controller & DTOs
  - ✅ Swagger documentation
  - ✅ Unit tests
  - ✅ Integration tests

- ✅ <b>Reset password endpoint</b>

  - ✅ Use case
  - ✅ Controller & DTOs
  - ✅ Swagger documentation
  - ✅ Unit tests
  - ✅ Integration tests

- [ ] <b>Auth Middleware</b>

  - [ ] Auth validator
  - [ ] Unit tests
  - [ ] Integration tests

- [ ] <b>Role Middleware</b>
  - [ ] Role validator
  - [ ] Unit tests
  - [ ] Integration tests
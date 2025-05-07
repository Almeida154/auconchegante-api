package br.com.auconchegante.integration.infra.web.controller;

import br.com.auconchegante.domain.port.outgoing.EmailProtocol;
import br.com.auconchegante.domain.type.UserRole;
import br.com.auconchegante.infra.persistence.entity.PasswordResetCodeEntity;
import br.com.auconchegante.infra.persistence.entity.UserEntity;
import br.com.auconchegante.infra.persistence.repository.PasswordResetCodeJpaRepository;
import br.com.auconchegante.infra.persistence.repository.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AuthControllerTest {
    @TestConfiguration
    static class TestConfig {
        @Bean
        public EmailProtocol emailProtocol() {
            return mock(EmailProtocol.class);
        }
    }

    @Autowired
    private EmailProtocol emailProtocol;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserJpaRepository userRepository;

    @Autowired
    private PasswordResetCodeJpaRepository passwordResetCodeJpaRepository;

    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_PASSWORD = "password123";
    private static final String TEST_CPF = "00546117090";
    private static final String TEST_NAME = "John Doe";
    private static final String TEST_CODE = "654321";

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        passwordResetCodeJpaRepository.deleteAll();
    }

    private UserEntity createTestUser() {
        UserEntity user = new UserEntity();
        user.setName(TEST_NAME);
        user.setEmail(TEST_EMAIL);
        user.setPassword(TEST_PASSWORD);
        user.setCpf(TEST_CPF);
        user.setPhone("11999999999");
        user.setRole(UserRole.HOST);
        user.setAvatarUrl("avatar.jpg");
        user.setRating(5.0);
        user.setActive(true);

        return userRepository.save(user);
    }

    private PasswordResetCodeEntity createTestPasswordResetCode() {
        PasswordResetCodeEntity passwordResetCode = new PasswordResetCodeEntity();
        passwordResetCode.setEmail(TEST_EMAIL);
        passwordResetCode.setCode(TEST_CODE);
        passwordResetCode.setExpiresAt(LocalDateTime.now().plusMinutes(30));

        return passwordResetCodeJpaRepository.save(passwordResetCode);
    }

    @Nested
    @DisplayName("sign-in")
    class SignIn {
        private String makeRequestBody(String email, String password) {
            return """
                    {
                        "email": "%s",
                        "password": "%s"
                    }
                    """.formatted(email, password);
        }

        @Test
        @DisplayName("Should return bad request when an invalid e-mail is provided")
        void signInBadRequest() throws Exception {
            String requestBody = makeRequestBody("invalid-email-format", TEST_PASSWORD);

            mockMvc.perform(post("/api/auth/sign-in")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Invalid e-mail."));
        }

        @Test
        @DisplayName("Should return forbidden when a wrong password is provided")
        void signInForbidden() throws Exception {
            createTestUser();

            String requestBody = makeRequestBody(TEST_EMAIL, "different_password123");

            mockMvc.perform(post("/api/auth/sign-in")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isForbidden())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Invalid password."));
        }

        @Test
        @DisplayName("Should authenticate user and return an access token")
        void signInSuccess() throws Exception {
            createTestUser();

            String requestBody = makeRequestBody(TEST_EMAIL, TEST_PASSWORD);

            mockMvc.perform(post("/api/auth/sign-in")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.accessToken").isNotEmpty());
        }
    }

    @Nested
    @DisplayName("sign-up")
    class SignUp {
        private String makeRequestBody(String email, String password, String cpf, String name) {
            return """
                    {
                        "email": "%s",
                        "password": "%s",
                        "cpf": "%s",
                        "name": "%s"
                    }
                    """.formatted(email, password, cpf, name);
        }


        @Test
        @DisplayName("Should return bad request when an invalid e-mail is provided")
        void signUpBadRequestForEmail() throws Exception {
            String requestBody =
                    makeRequestBody("invalid-email-format", TEST_PASSWORD, TEST_CPF, TEST_NAME);

            mockMvc.perform(post("/api/auth/sign-up")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Invalid e-mail."));
        }

        @Test
        @DisplayName("Should return conflict when e-mail exists")
        void signConflictForEmail() throws Exception {
            createTestUser();

            String requestBody =
                    makeRequestBody(TEST_EMAIL, TEST_PASSWORD, TEST_CPF, TEST_NAME);

            mockMvc.perform(post("/api/auth/sign-up")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isConflict())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("E-mail already in use."));
        }

        @Test
        @DisplayName("Should return bad request when an invalid CPF is provided")
        void signUpBadRequestForCPF() throws Exception {
            String requestBody =
                    makeRequestBody(TEST_EMAIL, TEST_PASSWORD, "invalid-cpf-format", TEST_NAME);

            mockMvc.perform(post("/api/auth/sign-up")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Invalid CPF."));
        }

        @Test
        @DisplayName("Should return conflict when CPF exists")
        void signConflictForCPF() throws Exception {
            createTestUser();

            String requestBody =
                    makeRequestBody("any@other.com", TEST_PASSWORD, TEST_CPF, TEST_NAME);

            mockMvc.perform(post("/api/auth/sign-up")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isConflict())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("CPF already in use."));
        }

        @Test
        @DisplayName("Should create user and return an access token")
        void signUpSuccess() throws Exception {
        }
    }

    @Nested
    @DisplayName("forgot-password")
    class ForgotPassword {
        private String makeRequestBody(String email) {
            return """
                    {
                        "email": "%s"
                    }
                    """.formatted(email);
        }

        @Test
        @DisplayName("Should return bad request when an invalid e-mail is provided")
        void forgotPasswordBadRequestForEmail() throws Exception {
            String requestBody =
                    makeRequestBody("invalid-email-format");

            mockMvc.perform(post("/api/auth/forgot-password")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Invalid e-mail."));
        }

        @Test
        @DisplayName("Should return not found when an unknown e-mail is provided")
        void forgotPasswordEmailNotFound() throws Exception {
            String requestBody = makeRequestBody(TEST_EMAIL);

            mockMvc.perform(post("/api/auth/forgot-password")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Unknown e-mail provided."));
        }

        @Test
        @DisplayName("Should return conflict when a valid code exists")
        void forgotPasswordConflict() throws Exception {
            createTestUser();
            createTestPasswordResetCode();

            String requestBody = makeRequestBody(TEST_EMAIL);

            mockMvc.perform(post("/api/auth/forgot-password")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isConflict())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message")
                            .value("A valid password reset code has already been sent to provided e-mail."));
        }

        @Test
        @DisplayName("Should return no content when the code is generated")
        void forgotPasswordSuccess() throws Exception {
            createTestUser();
            doNothing().when(emailProtocol).sendPasswordResetCode(anyString(), anyString(), anyString());

            String requestBody = makeRequestBody(TEST_EMAIL);

            mockMvc.perform(post("/api/auth/forgot-password")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isNoContent());
        }
    }

    @Nested
    @DisplayName("validate-password-reset-code")
    class ValidatePasswordResetCode {
        private String makeRequestBody(String code) {
            return """
                    {
                        "code": "%s"
                    }
                    """.formatted(code);
        }

        @Test
        @DisplayName("Should return success with false flag when not found")
        void forgotPasswordEmailNotFound() throws Exception {
            String requestBody = makeRequestBody(TEST_CODE);

            mockMvc.perform(post("/api/auth/validate-password-reset-code")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.valid").value(false));
        }

        @Test
        @DisplayName("Should return success with true flag when found")
        void forgotPasswordConflict() throws Exception {
            createTestPasswordResetCode();

            String requestBody = makeRequestBody(TEST_CODE);

            mockMvc.perform(post("/api/auth/validate-password-reset-code")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.valid").value(true));
        }
    }
}

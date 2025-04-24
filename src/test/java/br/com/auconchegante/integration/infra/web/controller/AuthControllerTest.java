package br.com.auconchegante.integration.infra.web.controller;

import br.com.auconchegante.domain.type.UserRole;
import br.com.auconchegante.infra.persistence.entity.UserEntity;
import br.com.auconchegante.infra.persistence.repository.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserJpaRepository userRepository;

    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_PASSWORD = "password123";
    private static final String TEST_CPF = "00546117090";
    private static final String TEST_NAME = "John Doe";

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
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
}

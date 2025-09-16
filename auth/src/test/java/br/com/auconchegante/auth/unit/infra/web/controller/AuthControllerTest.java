package br.com.auconchegante.auth.unit.infra.web.controller;

import br.com.auconchegante.auth.domain.model.User;
import br.com.auconchegante.auth.domain.port.incoming.*;
import br.com.auconchegante.auth.infra.web.controller.AuthController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    @Mock
    SignInUseCase signInUseCase;

    @Mock
    SignUpUseCase signUpUseCase;

    @Mock
    ForgotPasswordUseCase forgotPasswordUseCase;

    @Mock
    ValidatePasswordResetCodeUseCase validatePasswordResetCodeUseCase;

    @Mock
    UpdateForgottenPasswordUseCase updateForgottenPasswordUseCase;

    @InjectMocks
    AuthController authController;

    private MockMvc mockMvc;

    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_PASSWORD = "Password123#";
    private static final String TEST_ACCESS_TOKEN = "test.jwt.token";

    private User makeUser() {
        User user = new User();
        user.setName("John Doe");
        user.setCpf("00546117090");
        user.setEmail("david.santos@gmail.com");
        user.setPassword(TEST_PASSWORD);
        return user;
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(authController)
                .build();
    }

    @Test()
    @DisplayName("Should call sign in use case and return a token")
    void signIn() throws Exception {
        SignInUseCase.Result token = new SignInUseCase.Result(TEST_ACCESS_TOKEN);
        when(signInUseCase.execute(TEST_EMAIL, TEST_PASSWORD)).thenReturn(token);

        String requestBody = """
                {
                    "email": "%s",
                    "password": "%s"
                }
                """.formatted(TEST_EMAIL, TEST_PASSWORD);

        mockMvc.perform(post("/api/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(TEST_ACCESS_TOKEN));

        verify(signInUseCase).execute(TEST_EMAIL, TEST_PASSWORD);
    }

    @Test()
    @DisplayName("Should call sign up use case and return a token")
    void signUp() throws Exception {
        User user = makeUser();

        SignUpUseCase.Result token = new SignUpUseCase.Result(TEST_ACCESS_TOKEN);
        when(signUpUseCase.execute(user)).thenReturn(token);

        String requestBody = """
                {
                    "email": "%s",
                    "password": "%s",
                    "cpf": "%s",
                    "name": "%s"
                }
                """.formatted(user.getEmail(), user.getPassword(), user.getCpf(), user.getName());

        mockMvc.perform(post("/api/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(TEST_ACCESS_TOKEN));

        verify(signUpUseCase).execute(user);
    }

    @Test()
    @DisplayName("Should call forgot password use case and return void")
    void forgotPassword() throws Exception {
        String requestBody = """
                {
                    "email": "%s"
                }
                """.formatted(TEST_EMAIL);

        mockMvc.perform(post("/api/auth/forgot-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNoContent());

        verify(forgotPasswordUseCase).execute(TEST_EMAIL);
    }

    @Test()
    @DisplayName("Should call validate password reset code use case and return a result")
    void passwordResetCode() throws Exception {
        String code = "123456";

        String requestBody = """
                {
                    "code": "%s"
                }
                """.formatted(code);

        mockMvc.perform(post("/api/auth/validate-password-reset-code")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid").value(false));

        verify(validatePasswordResetCodeUseCase).execute(code);
    }

    @Test()
    @DisplayName("Should call update forgotten password use case and return a result")
    void updateForgottenPassword() throws Exception {
        String code = "123456";

        String requestBody = """
                {
                    "code": "%s",
                    "newPassword": "%s"
                }
                """.formatted(code, "anyNewPassword@");

        mockMvc.perform(post("/api/auth/update-forgotten-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNoContent());

        verify(updateForgottenPasswordUseCase).execute(code, "anyNewPassword@");
    }
}

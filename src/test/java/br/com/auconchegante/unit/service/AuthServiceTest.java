package br.com.auconchegante.unit.service;

import br.com.auconchegante.application.service.AuthService;
import br.com.auconchegante.domain.exceptions.ForbiddenException;
import br.com.auconchegante.domain.exceptions.NotFoundException;
import br.com.auconchegante.domain.model.User;
import br.com.auconchegante.domain.port.outgoing.persistence.UserProtocol;
import br.com.auconchegante.domain.port.outgoing.security.TokenProtocol;
import br.com.auconchegante.domain.type.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserProtocol userProtocol;

    @Mock
    private TokenProtocol tokenProtocol;

    @InjectMocks
    private AuthService authService;

    private static final String TEST_EMAIL = "trallallero@trallallá.com";
    private static final String TEST_PASSWORD = "any_password";

    private User makeUser(String email, String password) {
        return new User(
                UUID.randomUUID(),
                "Bombardiro Crocodillo",
                "12345678900",
                email,
                password,
                "11999999999",
                UserRole.HOST,
                "avatar.jpg",
                5.0,
                true
        );
    }

    private User makeUser() {
        return makeUser(TEST_EMAIL, TEST_PASSWORD);
    }

    @Test
    @DisplayName("Should call UserProtocol to find a user by e-mail")
    void callFindByEmail() {
        when(userProtocol.findByEmail(TEST_EMAIL))
                .thenReturn(Optional.of(makeUser()));

        authService.execute(TEST_EMAIL, TEST_PASSWORD);

        verify(userProtocol).findByEmail(TEST_EMAIL);
    }

    @Test
    @DisplayName("Should throw NotFoundException when given user is not found")
    void throwNotFoundException() {
        assertThatThrownBy(() -> {
            authService.execute(TEST_EMAIL, TEST_PASSWORD);
        })
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User not found.");
    }

    @Test
    @DisplayName("Should throw ForbiddenException when password is wrong")
    void throwForbiddenException() {
        when(userProtocol.findByEmail(TEST_EMAIL))
                .thenReturn(Optional.of(makeUser(TEST_EMAIL, "different_password")));

        assertThatThrownBy(() -> {
            this.authService.execute(TEST_EMAIL, TEST_PASSWORD);
        })
                .isInstanceOf(ForbiddenException.class)
                .hasMessage("Invalid password.");
    }

    @Test
    @DisplayName("Should call TokenProtocol to generate an access token")
    void callGenerate() {
        User user = makeUser();

        when(userProtocol.findByEmail(TEST_EMAIL))
                .thenReturn(Optional.of(user));

        authService.execute(TEST_EMAIL, TEST_PASSWORD);

        verify(tokenProtocol).generate(user);
    }
}

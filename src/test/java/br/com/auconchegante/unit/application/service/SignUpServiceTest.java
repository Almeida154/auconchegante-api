package br.com.auconchegante.unit.application.service;

import br.com.auconchegante.application.service.SignUpService;
import br.com.auconchegante.domain.exceptions.ConflictException;
import br.com.auconchegante.domain.exceptions.InternalException;
import br.com.auconchegante.domain.model.User;
import br.com.auconchegante.domain.port.outgoing.persistence.UserProtocol;
import br.com.auconchegante.domain.port.outgoing.security.EncryptionProtocol;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SignUpServiceTest {

    @Mock
    UserProtocol userProtocol;

    @Mock
    EncryptionProtocol encryptionProtocol;

    @Mock
    TokenProtocol tokenProtocol;

    @InjectMocks
    SignUpService signUpService;

    private static final String TEST_EMAIL = "trallallero@trallallá.com";
    private static final String TEST_CPF = "12345678900";
    private static final String TEST_PASSWORD = "any_password";

    private User makeUser(String email, String password) {
        return new User(
                UUID.randomUUID(),
                "Bombardiro Crocodillo",
                TEST_CPF,
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
        User user = makeUser();

        when(userProtocol.findByEmail(TEST_EMAIL)).thenReturn(Optional.empty());
        when(userProtocol.save(user)).thenReturn(Optional.of(user));

        signUpService.execute(user);

        verify(userProtocol).findByEmail(TEST_EMAIL);
    }

    @Test
    @DisplayName("Should throw ConflictException if e-mail exists")
    void throwConflictExceptionForEmail() {
        User user = makeUser();

        when(userProtocol.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> signUpService.execute(user))
                .isInstanceOf(ConflictException.class)
                .hasMessage("E-mail already in use.");
    }

    @Test
    @DisplayName("Should call UserProtocol to find a user by CPF")
    void callFindByCPF() {
        User user = makeUser();

        when(userProtocol.findByCPF(TEST_CPF)).thenReturn(Optional.empty());
        when(userProtocol.save(user)).thenReturn(Optional.of(user));

        signUpService.execute(user);

        verify(userProtocol).findByCPF(TEST_CPF);
    }

    @Test
    @DisplayName("Should throw ConflictException if CPF exists")
    void throwConflictExceptionForCPF() {
        User user = makeUser();

        when(userProtocol.findByCPF(TEST_CPF)).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> signUpService.execute(user))
                .isInstanceOf(ConflictException.class)
                .hasMessage("CPF already in use.");
    }

    @Test
    @DisplayName("Should call EncryptionProtocol to encrypt password")
    void callEncrypt() {
        User user = makeUser();
        String originalPassword = user.getPassword();

        when(userProtocol.findByEmail(TEST_EMAIL)).thenReturn(Optional.empty());
        when(userProtocol.findByCPF(TEST_CPF)).thenReturn(Optional.empty());
        when(userProtocol.save(user)).thenReturn(Optional.of(user));

        when(encryptionProtocol.encrypt(originalPassword)).thenReturn("encrypted_password");

        signUpService.execute(user);

        verify(encryptionProtocol).encrypt(originalPassword);
        assertThat(originalPassword).isNotEqualTo(user.getPassword());
        assertThat(user.getRole()).isEqualTo(UserRole.OWNER);
    }

    @Test
    @DisplayName("Should call UserProtocol to save a new user")
    void callSave() {
        User user = makeUser();
        String originalPassword = user.getPassword();

        when(userProtocol.findByEmail(TEST_EMAIL)).thenReturn(Optional.empty());
        when(userProtocol.findByCPF(TEST_CPF)).thenReturn(Optional.empty());
        when(userProtocol.save(user)).thenReturn(Optional.of(user));

        when(encryptionProtocol.encrypt(originalPassword)).thenReturn("encrypted_password");

        signUpService.execute(user);

        verify(userProtocol).save(user);
    }

    @Test
    @DisplayName("Should throw InternalException if user persistence went wrong")
    void throwInternalException() {
        User user = makeUser();
        String originalPassword = user.getPassword();

        when(userProtocol.findByEmail(TEST_EMAIL)).thenReturn(Optional.empty());
        when(userProtocol.findByCPF(TEST_CPF)).thenReturn(Optional.empty());
        when(userProtocol.save(user)).thenReturn(Optional.empty());

        when(encryptionProtocol.encrypt(originalPassword)).thenReturn("encrypted_password");

        assertThatThrownBy(() -> signUpService.execute(user))
                .isInstanceOf(InternalException.class)
                .hasMessage("Something went wrong trying to create a new user.");

    }

    @Test
    @DisplayName("Should call TokenProtocol to generate an access token")
    void callGenerate() {
        User user = makeUser();

        when(userProtocol.findByEmail(TEST_EMAIL)).thenReturn(Optional.empty());
        when(userProtocol.findByCPF(TEST_CPF)).thenReturn(Optional.empty());
        when(userProtocol.save(user)).thenReturn(Optional.of(user));

        signUpService.execute(user);

        verify(tokenProtocol).generate(user);
    }
}

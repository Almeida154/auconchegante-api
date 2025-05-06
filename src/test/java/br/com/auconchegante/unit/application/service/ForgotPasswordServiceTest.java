package br.com.auconchegante.unit.application.service;

import br.com.auconchegante.application.service.ForgotPasswordService;
import br.com.auconchegante.domain.exceptions.ConflictException;
import br.com.auconchegante.domain.exceptions.NotFoundException;
import br.com.auconchegante.domain.model.PasswordResetCode;
import br.com.auconchegante.domain.model.User;
import br.com.auconchegante.domain.port.outgoing.EmailProtocol;
import br.com.auconchegante.domain.port.outgoing.persistence.PasswordResetCodeProtocol;
import br.com.auconchegante.domain.port.outgoing.persistence.UserProtocol;
import br.com.auconchegante.domain.port.outgoing.security.CodeGeneratorProtocol;
import br.com.auconchegante.domain.type.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
public class ForgotPasswordServiceTest {

    @Mock
    private UserProtocol userProtocol;

    @Mock
    private PasswordResetCodeProtocol passwordResetCodeProtocol;

    @Mock
    private CodeGeneratorProtocol codeGeneratorProtocol;

    @Mock
    private EmailProtocol emailProtocol;

    @InjectMocks
    ForgotPasswordService forgotPasswordService;

    private static final String TEST_EMAIL = "trallallero@trallallá.com";

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


    private PasswordResetCode makePasswordResetCode(String email, String code) {
        return new PasswordResetCode(
                UUID.randomUUID(),
                email,
                code,
                null,
                LocalDateTime.now().plusMinutes(30)
        );
    }

    @Test
    @DisplayName("Should call UserProtocol to find the provided e-mail account")
    void callFindByEmail() {
        when(userProtocol.findByEmail(TEST_EMAIL)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            forgotPasswordService.execute(TEST_EMAIL);
        })
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Unknown e-mail provided.");

        verify(userProtocol).findByEmail(TEST_EMAIL);
    }

    @Test
    @DisplayName("Should call PasswordResetCodeProtocol to find a existing valid code")
    void callFindNotUsedOrExpiredByEmail() {
        when(userProtocol.findByEmail(TEST_EMAIL))
                .thenReturn(Optional.of(makeUser(TEST_EMAIL, "any_password")));

        when(passwordResetCodeProtocol.findNotUsedOrExpiredByEmail(TEST_EMAIL))
                .thenReturn(Optional.of(makePasswordResetCode(TEST_EMAIL, "123456")));

        assertThatThrownBy(() -> {
            forgotPasswordService.execute(TEST_EMAIL);
        })
                .isInstanceOf(ConflictException.class)
                .hasMessage("A valid password reset code has already been sent to provided e-mail.");

        verify(passwordResetCodeProtocol).findNotUsedOrExpiredByEmail(TEST_EMAIL);
    }

    @Test
    @DisplayName("Should call CodeGeneratorProtocol to generate a code")
    void callGenerate() {
        when(userProtocol.findByEmail(TEST_EMAIL))
                .thenReturn(Optional.of(makeUser(TEST_EMAIL, "any_password")));

        when(passwordResetCodeProtocol.findNotUsedOrExpiredByEmail(TEST_EMAIL))
                .thenReturn(Optional.empty());

        forgotPasswordService.execute(TEST_EMAIL);

        verify(codeGeneratorProtocol).generate();
    }

    @Test
    @DisplayName("Should call PasswordResetCodeProtocol to save a generated valid code")
    void callSave() {
        when(userProtocol.findByEmail(TEST_EMAIL))
                .thenReturn(Optional.of(makeUser(TEST_EMAIL, "any_password")));

        when(passwordResetCodeProtocol.findNotUsedOrExpiredByEmail(TEST_EMAIL))
                .thenReturn(Optional.empty());

        forgotPasswordService.execute(TEST_EMAIL);

        verify(passwordResetCodeProtocol).save(any(PasswordResetCode.class));
    }

    @Test
    @DisplayName("Should call EmailProtocol to send code by e-mail")
    void callSendPasswordResetCode() {
        when(userProtocol.findByEmail(TEST_EMAIL))
                .thenReturn(Optional.of(makeUser(TEST_EMAIL, "any_password")));

        when(passwordResetCodeProtocol.findNotUsedOrExpiredByEmail(TEST_EMAIL))
                .thenReturn(Optional.empty());

        when(codeGeneratorProtocol.generate()).thenReturn("654321");

        forgotPasswordService.execute(TEST_EMAIL);

        verify(emailProtocol).sendPasswordResetCode(TEST_EMAIL, "Bombardiro", "654321");
    }
}

package br.com.auconchegante.auth.unit.application.service;

import br.com.auconchegante.auth.application.service.UpdateForgottenPasswordService;
import br.com.auconchegante.auth.domain.exceptions.ForbiddenException;
import br.com.auconchegante.auth.domain.exceptions.NotFoundException;
import br.com.auconchegante.auth.domain.model.PasswordResetCode;
import br.com.auconchegante.auth.domain.model.User;
import br.com.auconchegante.auth.domain.port.outgoing.persistence.PasswordResetCodeProtocol;
import br.com.auconchegante.auth.domain.port.outgoing.persistence.UserProtocol;
import br.com.auconchegante.auth.domain.port.outgoing.security.EncryptionProtocol;
import br.com.auconchegante.auth.domain.type.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateForgottenPasswordServiceTest {
    @Mock
    private PasswordResetCodeProtocol passwordResetCodeProtocol;

    @Mock
    private UserProtocol userProtocol;

    @Mock
    private EncryptionProtocol encryptionProtocol;

    @InjectMocks
    UpdateForgottenPasswordService updateForgottenPasswordService;

    private static final String TEST_EMAIL = "kaueloviz@mail.com";
    private static final String TEST_PASSWORD = "any_password";
    private static final String TEST_NEW_PASSWORD = "any_NEW_password";
    private static final String TEST_CODE = "123456";

    private PasswordResetCode makePasswordResetCode() {
        return new PasswordResetCode(
                UUID.randomUUID(),
                TEST_EMAIL,
                TEST_CODE,
                null,
                LocalDateTime.now().plusMinutes(10)
        );
    }

    private User makeUser() {
        return new User(
                UUID.randomUUID(),
                "Kauê Loviz",
                "12345678900",
                TEST_EMAIL,
                TEST_PASSWORD,
                "11999999999",
                UserRole.HOST,
                "avatar.jpg",
                5.0,
                true
        );
    }

    @Test
    @DisplayName("Should throw ForbiddenException when password reset code is invalid")
    void throwForbiddenException() {
        assertThatThrownBy(() -> {
            when(passwordResetCodeProtocol.findNotUsedOrExpiredByCode(TEST_CODE))
                    .thenReturn(Optional.empty());

            updateForgottenPasswordService.execute(TEST_CODE, TEST_NEW_PASSWORD);
        }).isInstanceOf(ForbiddenException.class)
                .hasMessage("Invalid code provided.");
    }

    @Test
    @DisplayName("Should throw NotFoundException when password reset code email is not found")
    void throwNotFoundException() {
        assertThatThrownBy(() -> {
            PasswordResetCode passwordResetCode = makePasswordResetCode();

            when(passwordResetCodeProtocol.findNotUsedOrExpiredByCode(TEST_CODE))
                    .thenReturn(Optional.of(passwordResetCode));

            when(userProtocol.findByEmail(TEST_EMAIL)).thenReturn(Optional.empty());

            updateForgottenPasswordService.execute(TEST_CODE, TEST_NEW_PASSWORD);
        }).isInstanceOf(NotFoundException.class)
                .hasMessage("User not found.");
    }

    @Test
    @DisplayName("Should call EncryptionProtocol to encrypt the new password")
    void callEncrypt() {
        PasswordResetCode passwordResetCode = makePasswordResetCode();
        User user = makeUser();

        when(passwordResetCodeProtocol.findNotUsedOrExpiredByCode(TEST_CODE))
                .thenReturn(Optional.of(passwordResetCode));

        when(userProtocol.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(user));

        updateForgottenPasswordService.execute(TEST_CODE, TEST_NEW_PASSWORD);

        verify(encryptionProtocol).encrypt(TEST_NEW_PASSWORD);
    }

    @Test
    @DisplayName("Should call persistence protocols")
    void callPersistenceProtocols() {
        PasswordResetCode passwordResetCode = makePasswordResetCode();
        User user = makeUser();

        when(passwordResetCodeProtocol.findNotUsedOrExpiredByCode(TEST_CODE))
                .thenReturn(Optional.of(passwordResetCode));

        when(userProtocol.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(user));

        when(encryptionProtocol.encrypt(TEST_NEW_PASSWORD)).thenReturn("@#$encrypted");

        updateForgottenPasswordService.execute(TEST_CODE, TEST_NEW_PASSWORD);

        assertThat(user.getPassword()).isEqualTo("@#$encrypted");
        verify(userProtocol).save(user);

        assertThat(passwordResetCode.getUsedAt()).isNotNull();
        verify(passwordResetCodeProtocol).save(passwordResetCode);
    }
}

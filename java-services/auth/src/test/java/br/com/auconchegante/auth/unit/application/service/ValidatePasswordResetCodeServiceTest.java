package br.com.auconchegante.auth.unit.application.service;

import br.com.auconchegante.auth.application.service.ValidatePasswordResetCodeService;
import br.com.auconchegante.auth.domain.model.PasswordResetCode;
import br.com.auconchegante.auth.domain.port.outgoing.persistence.PasswordResetCodeProtocol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ValidatePasswordResetCodeServiceTest {

    @Mock
    private PasswordResetCodeProtocol passwordResetCodeProtocol;

    @InjectMocks
    ValidatePasswordResetCodeService validatePasswordResetCodeService;

    private static final String TEST_CODE = "654321";


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
    @DisplayName("Should call PasswordResetCodeProtocol to find a valid code")
    void callFindNotUsedOrExpiredByCode() {
        when(passwordResetCodeProtocol.findNotUsedOrExpiredByCode(TEST_CODE)).thenReturn(Optional.empty());

        validatePasswordResetCodeService.execute(TEST_CODE);

        verify(passwordResetCodeProtocol).findNotUsedOrExpiredByCode(TEST_CODE);
    }
}

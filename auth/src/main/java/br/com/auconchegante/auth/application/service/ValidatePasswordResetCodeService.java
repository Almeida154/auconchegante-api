package br.com.auconchegante.auth.application.service;

import br.com.auconchegante.auth.domain.model.PasswordResetCode;
import br.com.auconchegante.auth.domain.port.incoming.ValidatePasswordResetCodeUseCase;
import br.com.auconchegante.auth.domain.port.outgoing.persistence.PasswordResetCodeProtocol;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class ValidatePasswordResetCodeService implements ValidatePasswordResetCodeUseCase {
    private final PasswordResetCodeProtocol passwordResetCodeProtocol;

    @Override
    public boolean execute(String code) {
        log.info("Validating password reset code: {}", code);

        Optional<PasswordResetCode> passwordResetCode =
                passwordResetCodeProtocol.findNotUsedOrExpiredByCode(code);

        return passwordResetCode.isPresent();
    }
}

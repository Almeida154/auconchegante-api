package br.com.auconchegante.application.service;

import br.com.auconchegante.domain.model.PasswordResetCode;
import br.com.auconchegante.domain.port.incoming.ValidatePasswordResetCodeUseCase;
import br.com.auconchegante.domain.port.outgoing.persistence.PasswordResetCodeProtocol;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ValidatePasswordResetCodeService implements ValidatePasswordResetCodeUseCase {
    private static final long PASSWORD_RESET_EXPIRATION_MINUTES = 30;

    private final PasswordResetCodeProtocol passwordResetCodeProtocol;

    @Override
    public boolean execute(String code) {
        Optional<PasswordResetCode> passwordResetCode =
                passwordResetCodeProtocol.findByCode(code);

        return passwordResetCode.isPresent();
    }
}

package br.com.auconchegante.auth.application.service;

import br.com.auconchegante.auth.domain.model.PasswordResetCode;
import br.com.auconchegante.auth.domain.port.incoming.ValidatePasswordResetCodeUseCase;
import br.com.auconchegante.auth.domain.port.outgoing.persistence.PasswordResetCodeProtocol;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ValidatePasswordResetCodeService implements ValidatePasswordResetCodeUseCase {
    private final PasswordResetCodeProtocol passwordResetCodeProtocol;

    @Override
    public boolean execute(String code) {
        Optional<PasswordResetCode> passwordResetCode =
                passwordResetCodeProtocol.findNotUsedOrExpiredByCode(code);

        return passwordResetCode.isPresent();
    }
}

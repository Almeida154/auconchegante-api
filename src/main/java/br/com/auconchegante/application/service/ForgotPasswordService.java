package br.com.auconchegante.application.service;

import br.com.auconchegante.domain.exceptions.ConflictException;
import br.com.auconchegante.domain.exceptions.NotFoundException;
import br.com.auconchegante.domain.port.incoming.ForgotPasswordUseCase;
import br.com.auconchegante.domain.port.outgoing.persistence.PasswordResetCodeProtocol;
import br.com.auconchegante.domain.port.outgoing.persistence.UserProtocol;
import br.com.auconchegante.domain.port.outgoing.security.CodeGeneratorProtocol;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ForgotPasswordService implements ForgotPasswordUseCase {
    private final UserProtocol userProtocol;
    private final PasswordResetCodeProtocol passwordResetCodeProtocol;
    private final CodeGeneratorProtocol codeGeneratorProtocol;

    @Override
    public void execute(String email) {
        if (userProtocol.findByEmail(email).isEmpty()) {
            throw new NotFoundException("Unknown e-mail provided.");
        }

        if (passwordResetCodeProtocol.findNotUsedByEmail(email).isPresent()) {
            throw new ConflictException("A valid password reset code has already been sent to provided e-mail.");
        }

        String code = codeGeneratorProtocol.generate();

        // Enviar e-mail
    }
}

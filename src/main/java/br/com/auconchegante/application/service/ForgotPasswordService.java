package br.com.auconchegante.application.service;

import br.com.auconchegante.domain.exceptions.ConflictException;
import br.com.auconchegante.domain.exceptions.NotFoundException;
import br.com.auconchegante.domain.model.PasswordResetCode;
import br.com.auconchegante.domain.model.User;
import br.com.auconchegante.domain.port.incoming.ForgotPasswordUseCase;
import br.com.auconchegante.domain.port.outgoing.EmailProtocol;
import br.com.auconchegante.domain.port.outgoing.persistence.PasswordResetCodeProtocol;
import br.com.auconchegante.domain.port.outgoing.persistence.UserProtocol;
import br.com.auconchegante.domain.port.outgoing.security.CodeGeneratorProtocol;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ForgotPasswordService implements ForgotPasswordUseCase {
    private static final long PASSWORD_RESET_EXPIRATION_MINUTES = 30;

    private final UserProtocol userProtocol;
    private final PasswordResetCodeProtocol passwordResetCodeProtocol;
    private final CodeGeneratorProtocol codeGeneratorProtocol;
    private final EmailProtocol emailProtocol;

    @Override
    public void execute(String email) {
        Optional<User> user = userProtocol.findByEmail(email);

        if (user.isEmpty()) {
            throw new NotFoundException("Unknown e-mail provided.");
        }

        Optional<PasswordResetCode> existingValidCode = passwordResetCodeProtocol
                .findNotUsedOrExpiredByEmail(email);

        if (existingValidCode.isPresent()) {
            throw new ConflictException("A valid password reset code has already been sent to provided e-mail.");
        }

        String code = codeGeneratorProtocol.generate();

        PasswordResetCode passwordResetCode = new PasswordResetCode();
        passwordResetCode.setCode(code);
        passwordResetCode.setEmail(email);
        passwordResetCode.setExpiresAt(LocalDateTime.now().plusMinutes(PASSWORD_RESET_EXPIRATION_MINUTES));

        passwordResetCodeProtocol.save(passwordResetCode);

        String name = user.get().getName().split("\\s+")[0];
        emailProtocol.sendPasswordResetCode(email, name, code);
    }
}

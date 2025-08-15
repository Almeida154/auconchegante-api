package br.com.auconchegante.auth.application.service;

import br.com.auconchegante.auth.domain.exceptions.ForbiddenException;
import br.com.auconchegante.auth.domain.exceptions.NotFoundException;
import br.com.auconchegante.auth.domain.model.PasswordResetCode;
import br.com.auconchegante.auth.domain.model.User;
import br.com.auconchegante.auth.domain.port.incoming.UpdateForgottenPasswordUseCase;
import br.com.auconchegante.auth.domain.port.outgoing.persistence.PasswordResetCodeProtocol;
import br.com.auconchegante.auth.domain.port.outgoing.persistence.UserProtocol;
import br.com.auconchegante.auth.domain.port.outgoing.security.EncryptionProtocol;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UpdateForgottenPasswordService implements UpdateForgottenPasswordUseCase {
    private final PasswordResetCodeProtocol passwordResetCodeProtocol;
    private final UserProtocol userProtocol;
    private final EncryptionProtocol encryptionProtocol;

    @Override
    public void execute(String code, String newPassword) {
        Optional<PasswordResetCode> passwordResetCode = passwordResetCodeProtocol.findNotUsedOrExpiredByCode(code);

        if (passwordResetCode.isEmpty())
            throw new ForbiddenException("Already used or expired code provided.");

        Optional<User> user = userProtocol.findByEmail(passwordResetCode.get().getEmail());

        if (user.isEmpty())
            throw new NotFoundException("User not found.");

        String encryptedPassword = encryptionProtocol.encrypt(newPassword);
        user.get().setPassword(encryptedPassword);
        userProtocol.save(user.get());

        passwordResetCode.get().setUsedAt(LocalDateTime.now());
        passwordResetCodeProtocol.save(passwordResetCode.get());
    }
}

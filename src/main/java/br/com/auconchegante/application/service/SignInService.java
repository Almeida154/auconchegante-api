package br.com.auconchegante.application.service;

import br.com.auconchegante.domain.exceptions.ForbiddenException;
import br.com.auconchegante.domain.exceptions.NotFoundException;
import br.com.auconchegante.domain.model.User;
import br.com.auconchegante.domain.port.incoming.SignInUseCase;
import br.com.auconchegante.domain.port.outgoing.persistence.UserProtocol;
import br.com.auconchegante.domain.port.outgoing.security.EncryptionProtocol;
import br.com.auconchegante.domain.port.outgoing.security.TokenProtocol;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SignInService implements SignInUseCase {
    private final UserProtocol userProtocol;
    private final TokenProtocol tokenProtocol;
    private final EncryptionProtocol encryptionProtocol;

    @Override
    public Result execute(String email, String password) {
        User user = userProtocol.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found."));

        String decryptedPassword = encryptionProtocol.decrypt(user.getPassword());

        if (!decryptedPassword.equals(password)) {
            throw new ForbiddenException("Invalid password.");
        }

        String accessToken = this.tokenProtocol.generate(user);

        return new Result(accessToken);
    }
}

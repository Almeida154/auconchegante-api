package br.com.auconchegante.application.service;

import br.com.auconchegante.domain.model.User;
import br.com.auconchegante.domain.port.incoming.SignUpUseCase;
import br.com.auconchegante.domain.port.outgoing.persistence.UserProtocol;
import br.com.auconchegante.domain.port.outgoing.security.EncryptionProtocol;
import br.com.auconchegante.domain.port.outgoing.security.TokenProtocol;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SignUpService implements SignUpUseCase {
    private final UserProtocol userProtocol;
    private final EncryptionProtocol encryptionProtocol;

    @Override
    public Result execute(User user) {
        // Check unique fields (CPF, e-mail), if duplicated throw an exception

        // Make password encryption

        // Save new user and return an access token

        return new Result("");
    }
}

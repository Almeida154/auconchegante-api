package br.com.auconchegante.auth.application.service;

import br.com.auconchegante.auth.domain.exceptions.ConflictException;
import br.com.auconchegante.auth.domain.exceptions.InternalException;
import br.com.auconchegante.auth.domain.model.User;
import br.com.auconchegante.auth.domain.port.incoming.SignUpUseCase;
import br.com.auconchegante.auth.domain.port.outgoing.persistence.UserProtocol;
import br.com.auconchegante.auth.domain.port.outgoing.security.EncryptionProtocol;
import br.com.auconchegante.auth.domain.port.outgoing.security.TokenProtocol;
import br.com.auconchegante.auth.domain.type.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class SignUpService implements SignUpUseCase {
    private final UserProtocol userProtocol;
    private final EncryptionProtocol encryptionProtocol;
    private final TokenProtocol tokenProtocol;

    @Override
    public Result execute(User user) {
        log.info("Signing up a new user with email: {}", user.getEmail());

        if (userProtocol.findByEmail(user.getEmail()).isPresent())
            throw new ConflictException("E-mail already in use.");

        if (userProtocol.findByCPF(user.getCpf()).isPresent())
            throw new ConflictException("CPF already in use.");

        String encryptedPassword = encryptionProtocol.encrypt(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setRole(UserRole.OWNER);
        
        User created = userProtocol.save(user)
                .orElseThrow(() -> new InternalException("Something went wrong trying to create a new user."));

        String accessToken = tokenProtocol.generate(created);

        return new Result(accessToken);
    }
}

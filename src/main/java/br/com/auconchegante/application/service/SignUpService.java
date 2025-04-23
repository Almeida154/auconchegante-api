package br.com.auconchegante.application.service;

import br.com.auconchegante.domain.exceptions.ConflictException;
import br.com.auconchegante.domain.exceptions.InternalException;
import br.com.auconchegante.domain.model.User;
import br.com.auconchegante.domain.port.incoming.SignUpUseCase;
import br.com.auconchegante.domain.port.outgoing.persistence.UserProtocol;
import br.com.auconchegante.domain.port.outgoing.security.EncryptionProtocol;
import br.com.auconchegante.domain.port.outgoing.security.TokenProtocol;
import br.com.auconchegante.domain.type.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SignUpService implements SignUpUseCase {
    private final UserProtocol userProtocol;
    private final EncryptionProtocol encryptionProtocol;
    private final TokenProtocol tokenProtocol;

    @Override
    public Result execute(User user) {
        if (userProtocol.findByEmail(user.getEmail()).isPresent())
            throw new ConflictException("E-mail already in use.");

        if (userProtocol.findByCPF(user.getCpf()).isPresent())
            throw new ConflictException("CPF already in use.");

        String encryptedPassword = encryptionProtocol.encrypt(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setRole(UserRole.OWNER);

        System.out.println("OIIIIII");
        System.out.println(user.toString());

        User created = userProtocol.save(user)
                .orElseThrow(() -> new InternalException("Something went wrong trying to create a new user."));

        String accessToken = tokenProtocol.generate(created);

        return new Result(accessToken);
    }
}

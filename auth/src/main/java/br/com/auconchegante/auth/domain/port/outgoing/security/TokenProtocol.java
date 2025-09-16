package br.com.auconchegante.auth.domain.port.outgoing.security;

import br.com.auconchegante.auth.domain.model.User;

public interface TokenProtocol {
    String generate(User user);

    boolean validate(String token);
}

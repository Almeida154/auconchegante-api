package br.com.auconchegante.domain.port.outgoing.security;

import br.com.auconchegante.domain.model.User;

public interface TokenProtocol {
    String generate(User user);

    boolean validate(String token);
}

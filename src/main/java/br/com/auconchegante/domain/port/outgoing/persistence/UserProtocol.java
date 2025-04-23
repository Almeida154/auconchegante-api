package br.com.auconchegante.domain.port.outgoing.persistence;

import br.com.auconchegante.domain.model.User;

import java.util.Optional;

public interface UserProtocol {
    Optional<User> findByEmail(String email);

    Optional<User> save(User user);
}

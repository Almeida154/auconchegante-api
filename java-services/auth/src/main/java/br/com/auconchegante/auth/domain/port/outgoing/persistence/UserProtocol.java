package br.com.auconchegante.auth.domain.port.outgoing.persistence;

import br.com.auconchegante.auth.domain.model.User;

import java.util.Optional;

public interface UserProtocol {
    Optional<User> findByEmail(String email);

    Optional<User> findByCPF(String CPF);

    Optional<User> save(User user);
}

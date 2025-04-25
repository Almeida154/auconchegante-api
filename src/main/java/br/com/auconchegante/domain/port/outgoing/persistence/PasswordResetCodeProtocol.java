package br.com.auconchegante.domain.port.outgoing.persistence;

import br.com.auconchegante.domain.model.PasswordResetCode;

import java.util.Optional;

public interface PasswordResetCodeProtocol {
    Optional<PasswordResetCode> save(PasswordResetCode passwordResetCode);

    Optional<PasswordResetCode> findByCode(String code);

    Optional<PasswordResetCode> findNotUsedByEmail(String email);

    void markAsUsedByCode(String code);
}

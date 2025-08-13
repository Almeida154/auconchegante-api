package br.com.auconchegante.auth.domain.port.outgoing.persistence;

import br.com.auconchegante.auth.domain.model.PasswordResetCode;

import java.util.Optional;

public interface PasswordResetCodeProtocol {
    Optional<PasswordResetCode> save(PasswordResetCode passwordResetCode);

    Optional<PasswordResetCode> findByCode(String code);

    Optional<PasswordResetCode> findNotUsedOrExpiredByEmail(String email);

    Optional<PasswordResetCode> findNotUsedOrExpiredByCode(String code);

    void markAsUsedByCode(String code);
}

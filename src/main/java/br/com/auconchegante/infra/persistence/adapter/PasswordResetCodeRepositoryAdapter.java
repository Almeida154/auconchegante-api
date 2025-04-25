package br.com.auconchegante.infra.persistence.adapter;

import br.com.auconchegante.domain.model.PasswordResetCode;
import br.com.auconchegante.domain.port.outgoing.persistence.PasswordResetCodeProtocol;
import br.com.auconchegante.infra.persistence.mapper.PasswordResetCodeMapper;
import br.com.auconchegante.infra.persistence.repository.PasswordResetCodeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class PasswordResetCodeRepositoryAdapter implements PasswordResetCodeProtocol {
    private final PasswordResetCodeJpaRepository jpaRepository;
    private final PasswordResetCodeMapper mapper;

    @Override
    public Optional<PasswordResetCode> save(String email, String code) {
        return Optional.empty();
    }

    @Override
    public Optional<PasswordResetCode> findByCode(String code) {
        return Optional.empty();
    }

    @Override
    public void markAsUsedByCode(String code) {

    }
}

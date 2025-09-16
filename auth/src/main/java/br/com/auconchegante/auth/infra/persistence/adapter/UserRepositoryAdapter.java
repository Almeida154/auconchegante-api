package br.com.auconchegante.auth.infra.persistence.adapter;

import br.com.auconchegante.auth.domain.model.User;
import br.com.auconchegante.auth.domain.port.outgoing.persistence.UserProtocol;
import br.com.auconchegante.auth.infra.persistence.entity.UserEntity;
import br.com.auconchegante.auth.infra.persistence.mapper.UserMapper;
import br.com.auconchegante.auth.infra.persistence.repository.UserJpaRepository;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class UserRepositoryAdapter implements UserProtocol {
    private final UserJpaRepository jpaRepository;
    private final UserMapper mapper;

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByCPF(String cpf) {
        return jpaRepository.findByCpf(cpf)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> save(User user) {
        UserEntity userEntity = jpaRepository.save(mapper.toEntity(user));
        return Optional.ofNullable(mapper.toDomain(userEntity));
    }
}

package br.com.auconchegante.infra.persistence.adapter;

import br.com.auconchegante.domain.model.User;
import br.com.auconchegante.domain.port.outgoing.persistence.UserProtocol;
import br.com.auconchegante.infra.persistence.entity.UserEntity;
import br.com.auconchegante.infra.persistence.mapper.UserMapper;
import br.com.auconchegante.infra.persistence.repository.UserJpaRepository;
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
    public Optional<User> save(User user) {
        UserEntity userEntity = jpaRepository.save(mapper.toEntity(user));
        return Optional.ofNullable(mapper.toDomain(userEntity));
    }
}

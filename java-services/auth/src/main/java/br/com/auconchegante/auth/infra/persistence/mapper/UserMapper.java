package br.com.auconchegante.auth.infra.persistence.mapper;

import br.com.auconchegante.auth.domain.model.User;
import br.com.auconchegante.auth.infra.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toDomain(UserEntity entity) {
        if (entity == null) return null;
        return new User(
                entity.getId(),
                entity.getName(),
                entity.getCpf(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getPhone(),
                entity.getRole(),
                entity.getAvatarUrl(),
                entity.getRating(),
                entity.isActive()
        );
    }

    public UserEntity toEntity(User domain) {
        if (domain == null) return null;
        return UserEntity
                .builder()
                .id(domain.getId())
                .name(domain.getName())
                .cpf(domain.getCpf())
                .email(domain.getEmail())
                .password(domain.getPassword())
                .phone(domain.getPhone())
                .role(domain.getRole())
                .avatarUrl(domain.getAvatarUrl())
                .rating(domain.getRating())
                .active(domain.isActive())
                .build();
    }
}
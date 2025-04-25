package br.com.auconchegante.infra.persistence.mapper;

import br.com.auconchegante.domain.model.PasswordResetCode;
import br.com.auconchegante.infra.persistence.entity.PasswordResetCodeEntity;
import org.springframework.stereotype.Component;

@Component
public class PasswordResetCodeMapper {
    public PasswordResetCode toDomain(PasswordResetCodeEntity entity) {
        if (entity == null) return null;
        return new PasswordResetCode(
                entity.getId(),
                entity.getEmail(),
                entity.getCode(),
                entity.getUsedAt(),
                entity.getExpiresAt()
        );
    }

    public PasswordResetCodeEntity toEntity(PasswordResetCode domain) {
        if (domain == null) return null;
        return PasswordResetCodeEntity
                .builder()
                .id(domain.getId())
                .email(domain.getEmail())
                .code(domain.getCode())
                .usedAt(domain.getUsedAt())
                .expiresAt(domain.getExpiresAt())
                .build();
    }
}

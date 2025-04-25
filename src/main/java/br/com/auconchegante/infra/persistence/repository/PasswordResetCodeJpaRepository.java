package br.com.auconchegante.infra.persistence.repository;

import br.com.auconchegante.infra.persistence.entity.PasswordResetCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PasswordResetCodeJpaRepository extends JpaRepository<PasswordResetCodeEntity, UUID> {
    Optional<PasswordResetCodeEntity> findByCode(String code);
}

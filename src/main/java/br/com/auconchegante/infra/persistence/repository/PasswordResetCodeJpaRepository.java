package br.com.auconchegante.infra.persistence.repository;

import br.com.auconchegante.infra.persistence.entity.PasswordResetCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PasswordResetCodeJpaRepository extends JpaRepository<PasswordResetCodeEntity, UUID> {
    Optional<PasswordResetCodeEntity> findByCode(String code);

    @Query(
            value = """
                    SELECT * FROM password_reset_codes
                    WHERE email = :email
                    AND used_at IS NULL
                    AND expires_at > NOW()
                    LIMIT 1;
                    """,
            nativeQuery = true
    )
    Optional<PasswordResetCodeEntity> findNotUsedOrExpiredByEmail(@Param("email") String email);
}

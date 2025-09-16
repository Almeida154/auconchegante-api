package br.com.auconchegante.auth.unit.infra.persistence.mapper;

import br.com.auconchegante.auth.domain.model.PasswordResetCode;
import br.com.auconchegante.auth.infra.persistence.entity.PasswordResetCodeEntity;
import br.com.auconchegante.auth.infra.persistence.mapper.PasswordResetCodeMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordResetCodeMapperTest {
    PasswordResetCodeMapper mapper = new PasswordResetCodeMapper();

    private PasswordResetCode makePasswordResetCodeDomain() {
        return new PasswordResetCode(
                UUID.randomUUID(),
                "valid@email.com",
                "123456",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Test
    void toDomain() {
        PasswordResetCodeEntity entity = new PasswordResetCodeEntity();

        var domain = mapper.toDomain(entity);

        assertThat(domain).isInstanceOf(PasswordResetCode.class);
    }

    @Test
    void toDomainNullable() {
        PasswordResetCode domain = mapper.toDomain(null);

        assertThat(domain).isNull();
    }

    @Test
    void toEntity() {
        PasswordResetCode domain = makePasswordResetCodeDomain();

        var userEntity = mapper.toEntity(domain);

        assertThat(userEntity).isInstanceOf(PasswordResetCodeEntity.class);
    }

    @Test
    void toEntityNullable() {
        PasswordResetCodeEntity entity = mapper.toEntity(null);

        assertThat(entity).isNull();
    }
}

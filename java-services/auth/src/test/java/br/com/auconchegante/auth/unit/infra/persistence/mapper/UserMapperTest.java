package br.com.auconchegante.auth.unit.infra.persistence.mapper;

import br.com.auconchegante.auth.domain.model.User;
import br.com.auconchegante.auth.domain.type.UserRole;
import br.com.auconchegante.auth.infra.persistence.entity.UserEntity;
import br.com.auconchegante.auth.infra.persistence.mapper.UserMapper;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest {
    UserMapper mapper = new UserMapper();

    private User makeUserDomain() {
        return new User(
                UUID.randomUUID(),
                "Bombardiro Crocodillo",
                "12345678900",
                "test@example.com",
                "any_password",
                "11999999999",
                UserRole.HOST,
                "avatar.jpg",
                5.0,
                true
        );
    }

    @Test
    void toDomain() {
        UserEntity entity = new UserEntity();

        var domain = mapper.toDomain(entity);

        assertThat(domain).isInstanceOf(User.class);
    }

    @Test
    void toDomainNullable() {
        User domain = mapper.toDomain(null);

        assertThat(domain).isNull();
    }

    @Test
    void toEntity() {
        User domain = makeUserDomain();

        var userEntity = mapper.toEntity(domain);

        assertThat(userEntity).isInstanceOf(UserEntity.class);
    }

    @Test
    void toEntityNullable() {
        UserEntity entity = mapper.toEntity(null);

        assertThat(entity).isNull();
    }
}

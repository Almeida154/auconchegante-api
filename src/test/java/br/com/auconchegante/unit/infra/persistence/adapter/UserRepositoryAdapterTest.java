package br.com.auconchegante.unit.infra.persistence.adapter;

import br.com.auconchegante.domain.model.User;
import br.com.auconchegante.domain.type.UserRole;
import br.com.auconchegante.infra.persistence.adapter.UserRepositoryAdapter;
import br.com.auconchegante.infra.persistence.entity.UserEntity;
import br.com.auconchegante.infra.persistence.mapper.UserMapper;
import br.com.auconchegante.infra.persistence.repository.UserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryAdapterTest {
    @Mock
    private UserJpaRepository jpaRepository;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserRepositoryAdapter repositoryAdapter;

    private static final String TEST_EMAIL = "test@example.com";

    private User makeUser(String email) {
        return new User(
                UUID.randomUUID(),
                "Bombardiro Crocodillo",
                "12345678900",
                email,
                "any_password",
                "11999999999",
                UserRole.HOST,
                "avatar.jpg",
                5.0,
                true
        );
    }

    private User makeUser() {
        return makeUser(TEST_EMAIL);
    }

    @Test
    @DisplayName("Should call findByEmail JPA user repository")
    void findByEmail() {
        UserEntity userEntity = new UserEntity();
        User userDomain = makeUser();

        when(jpaRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(userEntity));
        when(mapper.toDomain(userEntity)).thenReturn(userDomain);

        Optional<User> result = repositoryAdapter.findByEmail(TEST_EMAIL);

        verify(jpaRepository).findByEmail(TEST_EMAIL);
        verify(mapper).toDomain(userEntity);

        assertThat(result).contains(userDomain);
    }

    @Test
    @DisplayName("Should call save JPA user repository")
    void save() {
        User userDomain = makeUser();
        UserEntity userEntity = mapper.toEntity(userDomain);

        when(jpaRepository.save(userEntity)).thenReturn(userEntity);
        when(mapper.toDomain(userEntity)).thenReturn(userDomain);

        Optional<User> result = repositoryAdapter.save(userDomain);

        verify(jpaRepository).save(userEntity);
        verify(mapper).toDomain(userEntity);

        assertThat(result).contains(userDomain);
    }
}

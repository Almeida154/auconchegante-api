package br.com.auconchegante.unit.infra.persistence.adapter;

import br.com.auconchegante.domain.model.PasswordResetCode;
import br.com.auconchegante.infra.persistence.adapter.PasswordResetCodeRepositoryAdapter;
import br.com.auconchegante.infra.persistence.entity.PasswordResetCodeEntity;
import br.com.auconchegante.infra.persistence.mapper.PasswordResetCodeMapper;
import br.com.auconchegante.infra.persistence.repository.PasswordResetCodeJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PasswordResetCodeRepositoryAdapterTest {

    @Mock
    PasswordResetCodeJpaRepository jpaRepository;

    @Mock
    PasswordResetCodeMapper mapper;

    @InjectMocks
    PasswordResetCodeRepositoryAdapter repositoryAdapter;

    private final String TEST_EMAIL = "valid@email.com";
    private final String TEST_CODE = "123456";

    private PasswordResetCode makePasswordResetCode() {
        PasswordResetCode passwordResetCode = new PasswordResetCode();
        passwordResetCode.setEmail(TEST_EMAIL);
        passwordResetCode.setCode(TEST_CODE);
        return passwordResetCode;
    }

    @Test
    @DisplayName("Should call save")
    void save() {
        PasswordResetCode passwordResetCodeDomain = makePasswordResetCode();
        PasswordResetCodeEntity passwordResetCodeEntity = mapper.toEntity(passwordResetCodeDomain);

        when(jpaRepository.save(passwordResetCodeEntity)).thenReturn(passwordResetCodeEntity);
        when(mapper.toDomain(passwordResetCodeEntity)).thenReturn(passwordResetCodeDomain);

        Optional<PasswordResetCode> result = repositoryAdapter
                .save(passwordResetCodeDomain);

        verify(jpaRepository).save(passwordResetCodeEntity);
        verify(mapper).toDomain(passwordResetCodeEntity);

        assertThat(result).contains(passwordResetCodeDomain);
    }

    @Test
    @DisplayName("Should call findByCode")
    void findByCode() {
        PasswordResetCodeEntity passwordResetCodeEntity = new PasswordResetCodeEntity();
        PasswordResetCode passwordResetCode = makePasswordResetCode();

        when(jpaRepository.findByCode(TEST_CODE)).thenReturn(Optional.of(passwordResetCodeEntity));
        when(mapper.toDomain(passwordResetCodeEntity)).thenReturn(passwordResetCode);

        Optional<PasswordResetCode> result = repositoryAdapter.findByCode(TEST_CODE);

        verify(jpaRepository).findByCode(TEST_CODE);
        verify(mapper).toDomain(passwordResetCodeEntity);

        assertThat(result).contains(passwordResetCode);
    }

    @Test
    @DisplayName("Should call markAsUsedByCode")
    void markAsUsedByCode() {
        PasswordResetCodeEntity passwordResetCodeEntity = new PasswordResetCodeEntity();

        when(jpaRepository.findByCode(TEST_CODE)).thenReturn(Optional.of(passwordResetCodeEntity));
        when(jpaRepository.save(passwordResetCodeEntity)).thenReturn(passwordResetCodeEntity);

        repositoryAdapter.markAsUsedByCode(TEST_CODE);

        verify(jpaRepository).findByCode(TEST_CODE);
        verify(jpaRepository).save(passwordResetCodeEntity);
    }

    @Test
    @DisplayName("Should call findNotUsedOrExpiredByEmail")
    void findNotUsedOrExpiredByEmail() {
        PasswordResetCodeEntity passwordResetCodeEntity = new PasswordResetCodeEntity();
        PasswordResetCode passwordResetCode = makePasswordResetCode();

        when(jpaRepository.findNotUsedOrExpiredByEmail(TEST_EMAIL)).thenReturn(Optional.of(passwordResetCodeEntity));
        when(mapper.toDomain(passwordResetCodeEntity)).thenReturn(passwordResetCode);

        Optional<PasswordResetCode> result = repositoryAdapter.findNotUsedOrExpiredByEmail(TEST_EMAIL);

        verify(jpaRepository).findNotUsedOrExpiredByEmail(TEST_EMAIL);
        verify(mapper).toDomain(passwordResetCodeEntity);

        assertThat(result).contains(passwordResetCode);
    }

    @Test
    @DisplayName("Should call findNotUsedOrExpiredByCode")
    void findNotUsedOrExpiredByCode() {
        PasswordResetCodeEntity passwordResetCodeEntity = new PasswordResetCodeEntity();
        PasswordResetCode passwordResetCode = makePasswordResetCode();

        when(jpaRepository.findNotUsedOrExpiredByCode(TEST_CODE)).thenReturn(Optional.of(passwordResetCodeEntity));
        when(mapper.toDomain(passwordResetCodeEntity)).thenReturn(passwordResetCode);

        Optional<PasswordResetCode> result = repositoryAdapter.findNotUsedOrExpiredByCode(TEST_CODE);

        verify(jpaRepository).findNotUsedOrExpiredByCode(TEST_CODE);
        verify(mapper).toDomain(passwordResetCodeEntity);

        assertThat(result).contains(passwordResetCode);
    }
}

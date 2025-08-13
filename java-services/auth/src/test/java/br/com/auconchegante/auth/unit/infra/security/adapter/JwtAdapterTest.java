package br.com.auconchegante.auth.unit.infra.security.adapter;

import br.com.auconchegante.auth.domain.exceptions.ForbiddenException;
import br.com.auconchegante.auth.domain.model.User;
import br.com.auconchegante.auth.domain.type.UserRole;
import br.com.auconchegante.auth.infra.security.adapter.JwtAdapter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class JwtAdapterTest {

    JwtAdapter jwtAdapter = new JwtAdapter();

    private static final String TEST_SECRET = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private static final Long TEST_EXPIRATION = 3600000L; // 1 hora

    @BeforeEach
    void setUp() {
        jwtAdapter = new JwtAdapter();
        // application.properties keys injection
        ReflectionTestUtils.setField(jwtAdapter, "secret", TEST_SECRET);
        ReflectionTestUtils.setField(jwtAdapter, "expiration", TEST_EXPIRATION);
    }

    private User makeUser() {
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
    @DisplayName("Should generate a valid JWT with correct claims")
    void generateToken() {
        User user = makeUser();

        String token = jwtAdapter.generate(user);

        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(
                        Keys.hmacShaKeyFor(
                                java.util.Base64.getDecoder().decode(TEST_SECRET)))
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertThat(token).isNotEmpty();
        assertThat(claims.getSubject()).isEqualTo(user.getEmail());
        assertThat(claims.get("userId")).isEqualTo(user.getId().toString());
        assertThat(claims.getExpiration())
                .isCloseTo(new java.util.Date(System.currentTimeMillis() + TEST_EXPIRATION), 1000);
    }

    @Test
    @DisplayName("Should validate a valid JWT token")
    void validateValidToken() {
        User user = makeUser();
        String token = jwtAdapter.generate(user);

        assertThat(jwtAdapter.validate(token)).isTrue();
    }

    @Test
    @DisplayName("Should throw exception for invalid token")
    void validateInvalidToken() {
        String invalidToken = "invalid.token.here";

        assertThatThrownBy(() -> jwtAdapter.validate(invalidToken))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Invalid JWT token.");
    }

    @Test
    @DisplayName("Should throw exception for expired token")
    void validateExpiredToken() {
        jwtAdapter = new JwtAdapter();
        ReflectionTestUtils.setField(jwtAdapter, "secret", TEST_SECRET);
        // Expired token
        ReflectionTestUtils.setField(jwtAdapter, "expiration", -1000L);

        User user = makeUser();
        String expiredToken = jwtAdapter.generate(user);

        assertThatThrownBy(() -> jwtAdapter.validate(expiredToken))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage("Invalid JWT token.");
    }
}

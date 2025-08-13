package br.com.auconchegante.auth.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class PasswordResetCode {
    private UUID id;
    private String email;
    private String code;
    private LocalDateTime usedAt;
    private LocalDateTime expiresAt;

    public PasswordResetCode() {
    }

    public PasswordResetCode(UUID id, String email, String code, LocalDateTime usedAt, LocalDateTime expiresAt) {
        this.id = id;
        this.email = email;
        this.code = code;
        this.usedAt = usedAt;
        this.expiresAt = expiresAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getUsedAt() {
        return usedAt;
    }

    public void setUsedAt(LocalDateTime usedAt) {
        this.usedAt = usedAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PasswordResetCode that = (PasswordResetCode) o;
        return Objects.equals(id, that.id) && Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code);
    }
}

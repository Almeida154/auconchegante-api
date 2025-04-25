package br.com.auconchegante.infra.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@Entity
@Table(name = "password_reset_codes")
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetCode {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Email
    @Size(min = 8)
    @Column(nullable = false)
    private String email;

    @NotBlank
    @Email
    @Size(min = 6, max = 6)
    @Column(nullable = false)
    private String code;

    @Column()
    private Date usedAt;

    @NotBlank
    @Column(nullable = false)
    private Date expiresAt;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

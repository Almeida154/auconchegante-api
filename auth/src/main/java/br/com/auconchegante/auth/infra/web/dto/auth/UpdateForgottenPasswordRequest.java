package br.com.auconchegante.auth.infra.web.dto.auth;

import br.com.auconchegante.auth.domain.validation.PasswordConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class UpdateForgottenPasswordRequest {
    @NotBlank(message = "Code is required.")
    @Length(min = 6, max = 6, message = "Invalid code.")
    private String code;

    @NotBlank(message = "Password is required.")
    @Length(min = 8, message = "Password must contain at least 8 characters.")
    @PasswordConstraint
    private String newPassword;
}

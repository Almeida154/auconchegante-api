package br.com.auconchegante.infra.web.dto.auth;

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

    @NotBlank(message = "New password is required.")
    private String newPassword;
}

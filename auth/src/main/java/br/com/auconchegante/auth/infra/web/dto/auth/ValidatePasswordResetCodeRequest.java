package br.com.auconchegante.auth.infra.web.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ValidatePasswordResetCodeRequest {
    @NotBlank(message = "Code is required.")
    @Length(min = 6, max = 6, message = "Invalid code.")
    private String code;
}

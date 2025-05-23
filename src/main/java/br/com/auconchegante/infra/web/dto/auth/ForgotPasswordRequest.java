package br.com.auconchegante.infra.web.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgotPasswordRequest {
    @NotBlank(message = "E-mail is required.")
    @Email(message = "Invalid e-mail.")
    private String email;
}

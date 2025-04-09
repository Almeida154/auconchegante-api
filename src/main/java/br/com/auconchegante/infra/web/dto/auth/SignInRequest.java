package br.com.auconchegante.infra.web.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInRequest {
    @NotBlank(message = "E-mail is required.")
    @Email(message = "Invalid e-mail.")
    private String email;

    @NotBlank(message = "Password is required.")
    private String password;
}

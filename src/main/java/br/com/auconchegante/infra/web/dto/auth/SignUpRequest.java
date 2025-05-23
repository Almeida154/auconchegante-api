package br.com.auconchegante.infra.web.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Data
public class SignUpRequest {
    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "CPF is required.")
    @CPF(message = "Invalid CPF.")
    private String cpf;

    @NotBlank(message = "E-mail is required.")
    @Email(message = "Invalid e-mail.")
    private String email;

    @NotBlank(message = "Password is required.")
    private String password;
}

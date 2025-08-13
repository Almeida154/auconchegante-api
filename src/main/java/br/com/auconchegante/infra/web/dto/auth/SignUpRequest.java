package br.com.auconchegante.infra.web.dto.auth;

import br.com.auconchegante.domain.validation.PasswordConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
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
    @Length(min = 8, message = "Password must contain at least 8 characters.")
    @PasswordConstraint
    private String password;
}

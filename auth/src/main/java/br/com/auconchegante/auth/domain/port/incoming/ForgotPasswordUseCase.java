package br.com.auconchegante.auth.domain.port.incoming;


public interface ForgotPasswordUseCase {
    void execute(String email);
}

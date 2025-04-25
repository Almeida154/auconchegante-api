package br.com.auconchegante.domain.port.incoming;


public interface ForgotPasswordUseCase {
    void execute(String email);
}

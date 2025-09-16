package br.com.auconchegante.auth.domain.port.incoming;


public interface UpdateForgottenPasswordUseCase {
    void execute(String code, String newPassword);
}

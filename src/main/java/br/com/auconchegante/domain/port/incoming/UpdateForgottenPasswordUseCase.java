package br.com.auconchegante.domain.port.incoming;


public interface UpdateForgottenPasswordUseCase {
    void execute(String code, String newPassword);
}

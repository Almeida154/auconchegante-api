package br.com.auconchegante.auth.domain.port.incoming;


public interface ValidatePasswordResetCodeUseCase {
    boolean execute(String code);
}

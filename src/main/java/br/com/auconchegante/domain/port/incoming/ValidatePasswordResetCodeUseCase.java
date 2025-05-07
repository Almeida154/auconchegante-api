package br.com.auconchegante.domain.port.incoming;


public interface ValidatePasswordResetCodeUseCase {
    boolean execute(String code);
}

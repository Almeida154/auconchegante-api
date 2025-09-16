package br.com.auconchegante.auth.domain.exceptions;

public class ConflictException extends AbstractException {
    public ConflictException(String message) {
        super("CONFLICT_EXCEPTION", message);
    }
}

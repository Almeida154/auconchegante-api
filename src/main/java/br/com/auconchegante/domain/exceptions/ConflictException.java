package br.com.auconchegante.domain.exceptions;

public class ConflictException extends AbstractException {
    public ConflictException(String message) {
        super("CONFLICT_EXCEPTION", message);
    }
}

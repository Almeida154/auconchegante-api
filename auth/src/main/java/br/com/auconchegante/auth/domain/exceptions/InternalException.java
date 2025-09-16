package br.com.auconchegante.auth.domain.exceptions;

public class InternalException extends AbstractException {
    public InternalException(String message) {
        super("INTERNAL_EXCEPTION", message);
    }
}

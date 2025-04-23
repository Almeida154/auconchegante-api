package br.com.auconchegante.domain.exceptions;

public class InternalException extends AbstractException {
    public InternalException(String message) {
        super("INTERNAL_EXCEPTION", message);
    }
}

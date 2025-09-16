package br.com.auconchegante.auth.domain.exceptions;

public class NotFoundException extends AbstractException {
    public NotFoundException(String message) {
        super("NOT_FOUND_EXCEPTION", message);
    }
}

package br.com.auconchegante.domain.exceptions;

public class ForbiddenException extends AbstractException {
    public ForbiddenException(String message) {
        super("FORBIDDEN_EXCEPTION", message);
    }
}

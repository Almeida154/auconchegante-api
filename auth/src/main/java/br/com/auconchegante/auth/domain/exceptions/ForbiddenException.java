package br.com.auconchegante.auth.domain.exceptions;

public class ForbiddenException extends AbstractException {
    public ForbiddenException(String message) {
        super("FORBIDDEN_EXCEPTION", message);
    }
}

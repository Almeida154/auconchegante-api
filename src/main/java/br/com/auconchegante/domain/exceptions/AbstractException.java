package br.com.auconchegante.domain.exceptions;

abstract public class AbstractException extends RuntimeException {
    private String code;
    private String message;

    public AbstractException(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

package br.com.auconchegante.infra.config;

import br.com.auconchegante.domain.exceptions.AbstractException;
import br.com.auconchegante.domain.exceptions.ForbiddenException;
import br.com.auconchegante.domain.exceptions.NotFoundException;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ErrorResponse(
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime timestamp,
            int status,
            String message
    ) {
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                message
        ));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedError(Exception exception) {
        // TODO: Register with some Logger
        return this.createErrorResponse(
                "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> forbidden(AbstractException ex) {
        return this.createErrorResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(AbstractException ex) {
        return this.createErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}

package br.com.auconchegante.infra.config;

import br.com.auconchegante.domain.exceptions.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
        System.out.println("-----------------");
        System.out.println("Error: " + exception.getMessage());
        System.out.println("Stack trace:");
        exception.printStackTrace();
        System.out.println("-----------------");
        return this.createErrorResponse(
                "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validation(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().stream()
                .findFirst()
                .map(ObjectError::getDefaultMessage)
                .orElse("Validation error.");

        return this.createErrorResponse(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<ErrorResponse> internal(AbstractException ex) {
        return this.createErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> conflict(AbstractException ex) {
        return this.createErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
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

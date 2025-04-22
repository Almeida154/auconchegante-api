package br.com.auconchegante.unit.infra.config;

import br.com.auconchegante.domain.exceptions.ForbiddenException;
import br.com.auconchegante.domain.exceptions.NotFoundException;
import br.com.auconchegante.infra.config.GlobalExceptionHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    @DisplayName("Should handle Unexpected exceptions")
    void unexpectedException() {
        String errorMessage = "Internal Server Error";
        Exception exception = new RuntimeException(errorMessage);

        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response =
                handler.handleUnexpectedError(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody())
                .extracting(GlobalExceptionHandler.ErrorResponse::message)
                .isEqualTo(errorMessage);
    }

    @Test
    @DisplayName("Should handle Validation exceptions")
    void validationException() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(exception.getBindingResult()).thenReturn(bindingResult);

        FieldError fieldError =
                new FieldError("any_object_name", "any_field", "Email is invalid.");

        when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError));

        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response =
                handler.validation(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody())
                .extracting(GlobalExceptionHandler.ErrorResponse::message)
                .isEqualTo("Email is invalid.");
    }

    @Test
    @DisplayName("Should handle ForbiddenException")
    void forbiddenException() {
        String errorMessage = "Password invalid.";
        ForbiddenException exception = new ForbiddenException(errorMessage);

        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response =
                handler.forbidden(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody())
                .extracting(GlobalExceptionHandler.ErrorResponse::message)
                .isEqualTo(errorMessage);
    }

    @Test
    @DisplayName("Should handle NotFoundException")
    void notFoundException() {
        String errorMessage = "User not found.";
        NotFoundException exception = new NotFoundException(errorMessage);

        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response =
                handler.notFound(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody())
                .extracting(GlobalExceptionHandler.ErrorResponse::message)
                .isEqualTo(errorMessage);
    }
}

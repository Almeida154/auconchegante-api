package br.com.auconchegante.auth.infra.web.controller;

import br.com.auconchegante.auth.domain.model.User;
import br.com.auconchegante.auth.domain.port.incoming.ForgotPasswordUseCase;
import br.com.auconchegante.auth.domain.port.incoming.SignInUseCase;
import br.com.auconchegante.auth.domain.port.incoming.SignUpUseCase;
import br.com.auconchegante.auth.domain.port.incoming.ValidatePasswordResetCodeUseCase;
import br.com.auconchegante.auth.infra.web.dto.auth.*;
import br.com.auconchegante.auth.domain.port.incoming.*;
import br.com.auconchegante.auth.infra.web.dto.auth.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/auth")
@Tag(name = "Authentication", description = "Authentication endpoints")
public class AuthController {
    private final SignInUseCase signInUseCase;
    private final SignUpUseCase signUpUseCase;
    private final ForgotPasswordUseCase forgotPasswordUseCase;
    private final ValidatePasswordResetCodeUseCase validatePasswordResetCodeUseCase;
    private final UpdateForgottenPasswordUseCase updateForgottenPasswordUseCase;

    @Operation(summary = "Sign in user", description = "Authenticate user with email and password")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = SignInResponse.class)))
    @PostMapping("sign-in")
    ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest request) {
        SignInUseCase.Result result = signInUseCase
                .execute(request.getEmail(), request.getPassword());

        return ResponseEntity.ok(SignInResponse
                .builder()
                .accessToken(result.accessToken())
                .build());
    }

    // TODO: Create endpoint for sign in a new host user. We gonna need to update its ROLE to HOST.

    // TODO: Improve password validation (like format, size, special characters etc.).

    @Operation(summary = "Sign up user", description = "Create and authenticate a new user")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = SignUpResponse.class)))
    @PostMapping("sign-up")
    ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        User user = new User();

        user.setName(request.getName());
        user.setCpf(request.getCpf());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        SignUpUseCase.Result result = signUpUseCase.execute(user);

        return ResponseEntity.ok(SignUpResponse
                .builder()
                .accessToken(result.accessToken())
                .build());
    }

    @Operation(summary = "Generate password recovery code", description = "Send a generated code to provided e-mail")
    @ApiResponse(responseCode = "204", content = @Content(schema = @Schema(implementation = Void.class)))
    @PostMapping("forgot-password")
    ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        forgotPasswordUseCase.execute(request.getEmail());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Validate code", description = "Check if a password reset code is valid")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ValidatePasswordResetCodeResponse.class)))
    @PostMapping("validate-password-reset-code")
    ResponseEntity<ValidatePasswordResetCodeResponse> validatePasswordResetCode(@Valid @RequestBody ValidatePasswordResetCodeRequest request) {
        boolean result = validatePasswordResetCodeUseCase.execute(request.getCode());
        return ResponseEntity.ok(ValidatePasswordResetCodeResponse
                .builder()
                .isValid(result)
                .build());
    }

    @Operation(summary = "Update an account password", description = "Receive a new password and update user account")
    @ApiResponse(responseCode = "204", content = @Content(schema = @Schema(implementation = Void.class)))
    @PostMapping("update-forgotten-password")
    ResponseEntity<Void> updateForgottenPasswordResponse(@Valid @RequestBody UpdateForgottenPasswordRequest request) {
        updateForgottenPasswordUseCase.execute(request.getCode(), request.getNewPassword());
        return ResponseEntity.noContent().build();
    }
}

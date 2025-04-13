package br.com.auconchegante.infra.web.controller;

import br.com.auconchegante.domain.port.incoming.SignInUseCase;
import br.com.auconchegante.infra.web.dto.auth.SignInRequest;
import br.com.auconchegante.infra.web.dto.auth.SignInResponse;
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
    private final SignInUseCase authUseCase;

    @Operation(summary = "Sign in user", description = "Authenticate user with email and password")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = SignInResponse.class)))
    @PostMapping("sign-in")
    ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest request) {
        SignInUseCase.Result result = this.authUseCase
                .execute(request.getEmail(), request.getPassword());

        return ResponseEntity.ok(SignInResponse
                .builder()
                .accessToken(result.accessToken())
                .build());
    }


    @GetMapping("sign-up")
    String signUp() {
        return "signUp";
    }

    @GetMapping("forgot-password")
    String forgotPassword() {
        return "forgotPassword";
    }

}

package br.com.auconchegante.infra.web.controller;

import br.com.auconchegante.domain.port.incoming.SignInUseCase;
import br.com.auconchegante.infra.web.dto.auth.SignInRequest;
import br.com.auconchegante.infra.web.dto.auth.SignInResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final SignInUseCase authUseCase;

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

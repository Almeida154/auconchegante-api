package br.com.auconchegante.auth.domain.port.incoming;

public interface SignInUseCase {
    public record Result(String accessToken) {
    }

    Result execute(String email, String password);
}

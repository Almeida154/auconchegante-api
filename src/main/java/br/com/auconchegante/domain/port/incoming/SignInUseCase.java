package br.com.auconchegante.domain.port.incoming;

public interface SignInUseCase {
    public record Result(String accessToken) {
    }

    Result execute(String email, String password);
}

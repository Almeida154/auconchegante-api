package br.com.auconchegante.domain.port.incoming;

public interface AuthUseCase {
    public record Result(String accessToken) {
    }

    Result execute(String email, String password);
}

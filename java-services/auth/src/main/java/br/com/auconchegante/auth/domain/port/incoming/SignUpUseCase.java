package br.com.auconchegante.auth.domain.port.incoming;

import br.com.auconchegante.auth.domain.model.User;

public interface SignUpUseCase {
    public record Result(String accessToken) {
    }

    Result execute(User user);
}

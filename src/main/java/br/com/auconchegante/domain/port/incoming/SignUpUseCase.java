package br.com.auconchegante.domain.port.incoming;

import br.com.auconchegante.domain.model.User;

public interface SignUpUseCase {
    public record Result(String accessToken) {
    }

    Result execute(User user);
}

package br.com.auconchegante.application.service;

import br.com.auconchegante.domain.port.incoming.ForgotPasswordUseCase;
import br.com.auconchegante.domain.port.outgoing.security.CodeGeneratorProtocol;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ForgotPasswordService implements ForgotPasswordUseCase {
    private final CodeGeneratorProtocol codeGeneratorProtocol;

    @Override
    public void execute(String email) {
        // E-mail existe na base de dados?

        // Já existe código para o e-mail não expirado?

        // Gerar código
        String code = codeGeneratorProtocol.generate();

        // Enviar e-mail
    }
}

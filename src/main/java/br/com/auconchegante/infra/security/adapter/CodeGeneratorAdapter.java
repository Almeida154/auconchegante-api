package br.com.auconchegante.infra.security.adapter;

import br.com.auconchegante.domain.port.outgoing.security.CodeGeneratorProtocol;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CodeGeneratorAdapter implements CodeGeneratorProtocol {
    @Override
    public String generate() {
        int random = new Random().nextInt(100000, 999999);
        return Integer.toString(random);
    }
}

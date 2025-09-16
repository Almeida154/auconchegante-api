package br.com.auconchegante.auth.infra.security.adapter;

import br.com.auconchegante.auth.domain.port.outgoing.security.EncryptionProtocol;
import jakarta.annotation.PostConstruct;
import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class JasyptAdapter implements EncryptionProtocol {
    @Value("${encryption.secret}")
    private String secret;

    private AES256TextEncryptor textEncryptor;

    @PostConstruct
    public void init() {
        this.textEncryptor = new AES256TextEncryptor();
        this.textEncryptor.setPassword(secret);
    }

    @Override
    public String encrypt(String input) {
        return textEncryptor.encrypt(input);
    }

    @Override
    public String decrypt(String input) {
        return textEncryptor.decrypt(input);
    }
}

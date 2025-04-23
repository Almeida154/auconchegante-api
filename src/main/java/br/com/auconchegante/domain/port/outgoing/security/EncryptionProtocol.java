package br.com.auconchegante.domain.port.outgoing.security;

public interface EncryptionProtocol {
    String encrypt(String input);

    String decrypt(String input);
}

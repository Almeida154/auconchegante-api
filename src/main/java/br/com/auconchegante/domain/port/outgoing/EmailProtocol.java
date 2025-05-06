package br.com.auconchegante.domain.port.outgoing;

public interface EmailProtocol {
    void sendPasswordResetCode(String email, String name, String code);
}

package br.com.auconchegante.auth.domain.port.outgoing;

public interface EmailProtocol {
    void sendPasswordResetCode(String email, String name, String code);
}

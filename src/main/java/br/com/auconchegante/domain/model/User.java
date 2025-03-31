package br.com.auconchegante.domain.model;

import java.util.UUID;

public class User {
    private final UUID id;
    private String name;
    private String email;
    private String password;

    public User() {
        this.id = UUID.randomUUID();
    }

    public User(String name, String email, String password) {
        this.id = UUID.randomUUID();
        setName(name);
        setEmail(email);
        setPassword(password);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

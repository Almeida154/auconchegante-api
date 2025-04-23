package br.com.auconchegante.domain.model;

import br.com.auconchegante.domain.type.UserRole;

import java.util.UUID;

public class User {
    private UUID id;
    private String name;
    private String cpf;
    private String email;
    private String password;
    private String phone;
    private UserRole role;
    private String avatarUrl;
    private double rating;
    private boolean active;

    public User() {
    }

    public User(UUID id, String name, String cpf, String email, String password,
                String phone, UserRole role, String avatarUrl, double rating, boolean active) {
        setId(id);
        setName(name);
        setCpf(cpf);
        setEmail(email);
        setPassword(password);
        setPhone(phone);
        setRole(role);
        setAvatarUrl(avatarUrl);
        setRating(rating);
        setActive(active);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatar_url) {
        this.avatarUrl = avatar_url;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

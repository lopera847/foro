package com.example.forum.dto;

public class CredentialsDTO {

    private String email;
    private String password;

    public CredentialsDTO() {
        // Constructor vacío requerido por algunos frameworks y por ser un DTO
    }

    public CredentialsDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters y Setters

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
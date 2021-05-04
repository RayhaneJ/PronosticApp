package com.example.pronosticapp;

public class User {
    private long id;
    private String email;
    private String nom;
    private String prenom;
    private Role role;

    public User(long rowId, String nom, String jeanlouis, String s) {}

    public User(String email, String nom, String prenom, Role role) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
    }

    public User(long id, String email, String nom, String prenom, Role role) {
        this.id = id;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Role getRole() {
        return role;
    }

    public long getId() {
        return id;
    }
}

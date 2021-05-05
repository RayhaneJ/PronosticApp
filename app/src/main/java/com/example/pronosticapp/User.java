package com.example.pronosticapp;

public class User {
    private long id;
    private String email;
    private String motDePasse;
    private String nom;
    private String prenom;
    private Role role;

    public User() {}

    public User(String email,String motDePasse, String nom, String prenom, Role role) {
        this.email = email;
        this.motDePasse = motDePasse;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
    }

    public User(long id, String email, String motDePasse, String nom, String prenom, Role role) {
        this.id = id;
        this.email = email;
        this.motDePasse = motDePasse;
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

    public String getMotDePasse() {
        return motDePasse;
    }
}

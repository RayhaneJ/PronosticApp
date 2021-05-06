package com.example.pronosticapp;

public class Rencontre {
    private long id;
    private String nom;
    private String date;
    private String championnat;
    private String equipeLocal;
    private String equipeVisiteur;
    private String equipeFavorite;
    public Rencontre() {}

    public Rencontre(String nom, String date, String championnat, String equipeLocal, String equipeVisiteur, String equipeFavorite) {
        this.nom = nom;
        this.date = date;
        this.championnat = championnat;
        this.equipeLocal = equipeLocal;
        this.equipeVisiteur = equipeVisiteur;
        this.equipeFavorite = equipeFavorite;
    }

    public Rencontre(long id, String nom, String date, String championnat, String equipeLocal, String equipeVisiteur, String equipeFavorite) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.championnat = championnat;
        this.equipeLocal = equipeLocal;
        this.equipeVisiteur = equipeVisiteur;
        this.equipeFavorite = equipeFavorite;
    }

    public String getNom() {
        return nom;
    }

    public String getDate() {
        return date;
    }

    public String getChampionnat() {
        return championnat;
    }

    public String getEquipeLocal() {
        return equipeLocal;
    }

    public String getEquipeVisiteur() {
        return equipeVisiteur;
    }

    public String getEquipeFavorite() {
        return equipeFavorite;
    }

    public long getId() {
        return id;
    }
}

package com.example.health_care.models;



public class Patient {
    private String nom;
    private  String prenom;
    private String dateNaissance;
    private boolean situationFamiliale;
    private String tel;
    private String email;
    private String adresse;

    public Patient() {
    }

    public Patient(String nom, String prenom, String dateNaissance, boolean situationFamiliale, String tel, String email, String adresse) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.situationFamiliale = situationFamiliale;
        this.tel = tel;
        this.email = email;
        this.adresse = adresse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public boolean isSituationFamiliale() {
        return situationFamiliale;
    }

    public void setSituationFamiliale(boolean situationFamiliale) {
        this.situationFamiliale = situationFamiliale;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return nom+" "+prenom;
    }
}

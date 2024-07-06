package com.transferArgent.model;

public class Frais {

    private int id;
    private String idfrais;
    private float montant1;
    private float montant2;
    private float frais;

    public Frais() {
    }

    public Frais(int id, String idfrais, float montant1, float montant2, float frais) {
        this.id = id;
        this.idfrais = idfrais;
        this.montant1 = montant1;
        this.montant2 = montant2;
        this.frais = frais;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdfrais() {
        return idfrais;
    }

    public void setIdfrais(String idfrais) {
        this.idfrais = idfrais;
    }

    public float getMontant1() {
        return montant1;
    }

    public void setMontant1(float montant1) {
        this.montant1 = montant1;
    }

    public float getMontant2() {
        return montant2;
    }

    public void setMontant2(float montant2) { // Correction du type de paramètre
        this.montant2 = montant2;
    }

    public float getFrais() {
        return frais;
    }

    public void setFrais(float frais) {
        this.frais = frais;
    }
}

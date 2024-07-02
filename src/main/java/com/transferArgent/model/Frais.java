package com.transferArgent.model;

public class Frais {
	
	private String idfrais;
	private float montant1;
	private float montant2;
	private float frais;
	
	
	public Frais() {
		
	}


	public Frais(String idfrais, float montant1, float montant2, float frais) {
		super();
		this.idfrais = idfrais;
		this.montant1 = montant1;
		this.montant2 = montant2;
		this.frais = frais;
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


	public void setMontant2(int montant2) {
		this.montant2 = montant2;
	}


	public float getFrais() {
		return frais;
	}


	public void setFrais( float frais) {
		this.frais = frais;
	}
	
	
}

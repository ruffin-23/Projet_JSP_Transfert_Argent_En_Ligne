package com.transferArgent.model;

public class Taux {

	private String idtaux;
	private float montant1;
	private float montant2;
	
	
	public Taux(String idtaux, float montant1, float montant2) {
		super();
		this.idtaux = idtaux;
		this.montant1 = montant1;
		this.montant2 = montant2;
	}


	public Taux() {
		super();
	}


	public String getIdtaux() {
		return idtaux;
	}


	public void setIdtaux(String idtaux) {
		this.idtaux = idtaux;
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


	public void setMontant2(float montant2) {
		this.montant2 = montant2;
	}
	
	
	
}

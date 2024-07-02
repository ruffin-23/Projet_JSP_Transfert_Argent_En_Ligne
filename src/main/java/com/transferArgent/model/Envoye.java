package com.transferArgent.model;

public class Envoye {
	private String idEnv;
	private String numEnvoyeur;
	private String numRecepteur;
	private float montant;
	private String date;
	private String raison;
	
	
	public Envoye (String idEnv, String numEnvoyeur, String numRecepteur, float montant, String date, String raison) {
		super();
		this.idEnv = idEnv;
		this.numEnvoyeur = numEnvoyeur;
		this.numRecepteur = numRecepteur;
		this.montant = montant;
		this.date = date;
		this.raison = raison;		
	}
	
	public Envoye(String numEnvoyeur, String numRecepteur, float montant, String date, String raison) {

		this.numEnvoyeur = numEnvoyeur;
		this.numRecepteur = numRecepteur;
		this.montant = montant;
		this.date = date;
		this.raison = raison;
	}

	public String getIdEnv() {
		return idEnv;
	}

	public void setIdEnv(String idEnv) {
		this.idEnv = idEnv;
	}

	public String getNumEnvoyeur() {
		return numEnvoyeur;
	}

	public void setNumEnvoyeur(String numEnvoyeur) {
		this.numEnvoyeur = numEnvoyeur;
	}

	public String getNumRecepteur() {
		return numRecepteur;
	}

	public void setNumRecepteur(String numRecepteur) {
		this.numRecepteur = numRecepteur;
	}

	public float getMontant() {
		return montant;
	}

	public void setMontant(float montant) {
		this.montant = montant;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRaison() {
		return raison;
	}

	public void setRaison(String raison) {
		this.raison = raison;
	}
	
}

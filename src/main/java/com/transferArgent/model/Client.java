package com.transferArgent.model;

public class Client {

	private String numtel;
	private String nom;
	private String sexe;
	private String pays;
	private float solde;
	private String mail;
	
	// Constructeur par défaut
	public Client(String nom, String sexe, String pays, float solde, String mail) {
		super();
		this.nom = nom;
		this.sexe = sexe;
		this.pays = pays;
		this.solde = solde;
		this.mail = mail;
	}

	// Constructeur avec paramètres
	public Client(String numtel, String nom, String sexe, String pays, float solde, String mail) {
		super();
		this.numtel = numtel;
		this.nom = nom;
		this.sexe = sexe;
		this.pays = pays;
		this.solde = solde;
		this.mail = mail;
	}


	public Client(String numtel) {
        this.numtel = numtel;
    }

    public String getNumtel() {
        return numtel;
    }

	public void setNumtel(String numtel) {
		this.numtel = numtel;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public float getSolde() {
		return solde;
	}

	public void setSolde(float solde) {
		this.solde = solde;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	
	
}

package model;

import java.sql.Timestamp;

public class Stock {
	private int id;
	private int idProduit;
	private int idRack;
	private int quantite;
	private Timestamp dateAjout;
	private String nomProduit = "toto";

	public String getNomProduit() {
	    return nomProduit;
	}

	public void setNomProduit(String nomProduit) {
	    this.nomProduit = nomProduit;
	}


	public Stock(int id, int idProduit, int idRack, int quantite, Timestamp dateAjout) {
		this.id = id;
		this.idProduit = idProduit;
		this.idRack = idRack;
		this.quantite = quantite;
		this.dateAjout = dateAjout;
	}

	public Stock(int idProduit, int idRack, int quantite) {
		this.idProduit = idProduit;
		this.idRack = idRack;
		this.quantite = quantite;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdProduit() {
		return idProduit;
	}

	public void setIdProduit(int idProduit) {
		this.idProduit = idProduit;
	}

	public int getIdRack() {
		return idRack;
	}

	public void setIdRack(int idRack) {
		this.idRack = idRack;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public Timestamp getDateAjout() {
		return dateAjout;
	}

	public void setDateAjout(Timestamp dateAjout) {
		this.dateAjout = dateAjout;
	}
}

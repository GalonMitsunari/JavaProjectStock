package model;

import java.sql.Timestamp;

public class MouvementStock {
	    private int id;
	    private int idProduit;
	    private String typeMouvement; // ENTREE ou SORTIE
	    private int quantite;
	    private int idRack;
	    private Timestamp dateMouvement;
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
	public String getTypeMouvement() {
		return typeMouvement;
	}
	public void setTypeMouvement(String typeMouvement) {
		this.typeMouvement = typeMouvement;
	}
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	public int getIdRack() {
		return idRack;
	}
	public void setIdRack(int idRack) {
		this.idRack = idRack;
	}
	public Timestamp getDateMouvement() {
		return dateMouvement;
	}
	public void setDateMouvement(Timestamp dateMouvement) {
		this.dateMouvement = dateMouvement;
	}

	}

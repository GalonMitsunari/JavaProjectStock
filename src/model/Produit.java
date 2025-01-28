package model;

import java.time.LocalDateTime;

public class Produit {
	private int id;
	private String nom;
	private String description;
	private String codeBarre;
	private String categorie;
	private LocalDateTime dateCreation;
	private int quantiteMin;

	public Produit(int id, String nom, String description, String codeBarre, String categorie,
			LocalDateTime dateCreation, int quantiteMin) {
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.codeBarre = codeBarre;
		this.categorie = categorie;
		this.quantiteMin = quantiteMin;
	    }

	    public int getQuantiteMin() {
	        return quantiteMin;
	    }

	    public void setQuantiteMin(int quantiteMin) {
	        this.quantiteMin = quantiteMin;
	    }
	

	public int getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public String getDescription() {
		return description;
	}

	public String getCodeBarre() {
		return codeBarre;
	}

	public String getCategorie() {
		return categorie;
	}

	public LocalDateTime getDateCreation() {
		return dateCreation;
	}
	@Override
	public String toString() {
	    return id + " : " + nom + " (" + categorie + ")";
	}

}

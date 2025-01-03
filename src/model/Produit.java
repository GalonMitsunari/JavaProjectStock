package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Produit {
	private int id;
	private String nom;
	private String description;
	private String codeBarre;
	private String categorie;
	private Timestamp dateCreation;

	public Produit(int id, String nom, String description, String codeBarre, String categorie,
			Timestamp dateCreation) {
		this.setId(id);
		this.setNom(nom);
		this.setDescription(description);
		this.setCodeBarre(codeBarre);
		this.setCategorie(categorie);
		this.setDateCreation(dateCreation);
	}

	public Produit(int int1, String string, String string2, String string3, String string4,
			LocalDateTime localDateTime) {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCodeBarre() {
		return codeBarre;
	}

	public void setCodeBarre(String codeBarre) {
		this.codeBarre = codeBarre;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public Timestamp getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Timestamp dateCreation) {
		this.dateCreation = dateCreation;
	}

}

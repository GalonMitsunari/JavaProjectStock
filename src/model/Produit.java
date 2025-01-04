package model;

import java.time.LocalDateTime;

public class Produit {
    private int id;
    private String nom;
    private String description;
    private String codeBarre;
    private String categorie;
    private LocalDateTime dateCreation;

    public Produit(int id, String nom, String description, String codeBarre, String categorie, LocalDateTime dateCreation) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.codeBarre = codeBarre;
        this.categorie = categorie;
        this.dateCreation = dateCreation;
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
}

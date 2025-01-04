package model;

import java.time.LocalDateTime;

public class MouvementStock {
    private int id;
    private int idProduit;
    private String typeMouvement;
    private int quantite;
    private Integer idRack; // Nullable
    private LocalDateTime dateMouvement;

    public MouvementStock(int id, int idProduit, String typeMouvement, int quantite, Integer idRack, LocalDateTime dateMouvement) {
        this.id = id;
        this.idProduit = idProduit;
        this.typeMouvement = typeMouvement;
        this.quantite = quantite;
        this.idRack = idRack;
        this.dateMouvement = dateMouvement;
    }

    public int getId() { return id; }
    public int getIdProduit() { return idProduit; }
    public String getTypeMouvement() { return typeMouvement; }
    public int getQuantite() { return quantite; }
    public Integer getIdRack() { return idRack; }
    public LocalDateTime getDateMouvement() { return dateMouvement; }

    public void setId(int id) { this.id = id; }
    public void setIdProduit(int idProduit) { this.idProduit = idProduit; }
    public void setTypeMouvement(String typeMouvement) { this.typeMouvement = typeMouvement; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    public void setIdRack(Integer idRack) { this.idRack = idRack; }
    public void setDateMouvement(LocalDateTime dateMouvement) { this.dateMouvement = dateMouvement; }
}

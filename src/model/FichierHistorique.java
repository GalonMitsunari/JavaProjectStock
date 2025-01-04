package model;

import java.time.LocalDateTime;

public class FichierHistorique {
    private int id;
    private String nomFichier;
    private LocalDateTime dateImport;
    private String status; // SUCCES ou ERREUR
    private String erreurs;

    public FichierHistorique(int id, String nomFichier, LocalDateTime dateImport, String status, String erreurs) {
        this.id = id;
        this.nomFichier = nomFichier;
        this.dateImport = dateImport;
        this.status = status;
        this.erreurs = erreurs;
    }

    public int getId() { return id; }
    public String getNomFichier() { return nomFichier; }
    public LocalDateTime getDateImport() { return dateImport; }
    public String getStatus() { return status; }
    public String getErreurs() { return erreurs; }

    public void setId(int id) { this.id = id; }
    public void setNomFichier(String nomFichier) { this.nomFichier = nomFichier; }
    public void setDateImport(LocalDateTime dateImport) { this.dateImport = dateImport; }
    public void setStatus(String status) { this.status = status; }
    public void setErreurs(String erreurs) { this.erreurs = erreurs; }
}

package model;

import java.security.Timestamp;

public class FichierHistorique {
	    private int id;
	    private String nomFichier;
	    private Timestamp dateImport;
	    private String status; // SUCCES ou ERREUR
	    private String erreurs;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNomFichier() {
		return nomFichier;
	}
	public void setNomFichier(String nomFichier) {
		this.nomFichier = nomFichier;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getDateImport() {
		return dateImport;
	}
	public void setDateImport(Timestamp dateImport) {
		this.dateImport = dateImport;
	}
	public String getErreurs() {
		return erreurs;
	}
	public void setErreurs(String erreurs) {
		this.erreurs = erreurs;
	}

	    // Constructeurs, getters, setters
	}

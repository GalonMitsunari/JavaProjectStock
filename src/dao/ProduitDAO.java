package dao;

import model.Produit;
import service.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitDAO {

    private static final String TABLE_NAME = "Produit";

    public void create(Produit produit) throws SQLException {
        String query = "INSERT INTO " + TABLE_NAME + " (nom, description, code_barre, categorie) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.connectToBDD();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, produit.getNom());
            pstmt.setString(2, produit.getDescription());
            pstmt.setString(3, produit.getCodeBarre());
            pstmt.setString(4, produit.getCategorie());
            pstmt.executeUpdate();
        }
    }

    public Produit readById(int id) throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection conn = DatabaseConnection.connectToBDD();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Produit(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getString("code_barre"),
                        rs.getString("categorie"),
                        rs.getTimestamp("date_creation").toLocalDateTime()
                    );
                }
            }
        }
        return null;
    }

    public List<Produit> readAll() throws SQLException {
        List<Produit> produits = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        try (Connection conn = DatabaseConnection.connectToBDD();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                produits.add(new Produit(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("description"),
                    rs.getString("code_barre"),
                    rs.getString("categorie"),
                    rs.getTimestamp("date_creation").toLocalDateTime()
                ));
            }
        }
        return produits;
    }

    public void updateProduit(Produit produit) throws SQLException {
	        String query = "UPDATE Produit SET nom = ?, description = ?, code_barre = ?, categorie = ? WHERE id = ?";
	        try (Connection conn = DatabaseConnection.connectToBDD();
	             PreparedStatement pstmt = conn.prepareStatement(query)) {
	            pstmt.setString(1, produit.getNom());
	            pstmt.setString(2, produit.getDescription());
	            pstmt.setString(3, produit.getCodeBarre());
	            pstmt.setString(4, produit.getCategorie());
	            pstmt.setInt(5, produit.getId());
	            pstmt.executeUpdate();
	        }
	    }

	    public void deleteProduitById(int id) throws SQLException {
	        String query = "DELETE FROM Produit WHERE id = ?";
	        try (Connection conn = DatabaseConnection.connectToBDD();
	             PreparedStatement pstmt = conn.prepareStatement(query)) {
	            pstmt.setInt(1, id);
	            pstmt.executeUpdate();
	        }
	    }

	    public List<Produit> getAllProduits() throws SQLException {
		    List<Produit> produits = new ArrayList<>();
		    String query = "SELECT * FROM Produit";

		    try (Connection conn = DatabaseConnection.connectToBDD();
		         Statement stmt = conn.createStatement();
		         ResultSet rs = stmt.executeQuery(query)) {

		        while (rs.next()) {
		            produits.add(new Produit(
		                rs.getInt("id"),
		                rs.getString("nom"),
		                rs.getString("description"),
		                rs.getString("code_barre"),
		                rs.getString("categorie"),
		                rs.getTimestamp("date_creation").toLocalDateTime() // Conversion
		            ));
		        }
		    }

		    return produits;
		}


}
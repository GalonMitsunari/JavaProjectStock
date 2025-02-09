package dao;

import model.Produit;
import service.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitDAO {

	private static final String TABLE_NAME = "Produit";

	public void create(Produit produit) throws SQLException {
		String query = "INSERT INTO " + TABLE_NAME
				+ " (nom, description, code_barre, categorie) VALUES (?, ?, ?, ?)";
		try (Connection conn = DatabaseConnection.connectToBDD();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, produit.getNom());
			pstmt.setString(2, produit.getDescription());
			pstmt.setString(3, produit.getCodeBarre());
			pstmt.setString(4, produit.getCategorie());
			pstmt.executeUpdate();
		}
	}

	public List<Produit> readAll() throws SQLException {
		List<Produit> produits = new ArrayList<>();
		String query = "SELECT * FROM " + TABLE_NAME;
		try (Connection conn = DatabaseConnection.connectToBDD();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				produits.add(new Produit(rs.getInt("id"), rs.getString("nom"),
						rs.getString("description"), rs.getString("code_barre"),
						rs.getString("categorie"),
						rs.getTimestamp("date_creation").toLocalDateTime(),
				                    rs.getInt("quantite_min")));
			}
		}
		return produits;
	}

	public Produit readProduitById(int id) throws SQLException {
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
							    rs.getTimestamp("date_creation") != null 
							        ? rs.getTimestamp("date_creation").toLocalDateTime() 
							        : null,
							    rs.getInt("quantite_min") // Ajoute la gestion de `quantite_min` si elle existe dans votre constructeur
							);
				}
			}
		}
		return null;
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
		String query = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
		try (Connection conn = DatabaseConnection.connectToBDD();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		}
	}

	public List<Produit> getAllProduits() throws SQLException {
		return readAll();
	}

	public List<Produit> getMissingStocks() throws SQLException {
		    List<Produit> produitsManquants = new ArrayList<>();

		    String query = "SELECT p.id, p.nom, p.description, p.code_barre, p.categorie,p.date_creation, p.quantite_min, "
		            + "IFNULL(s.quantite, 0) AS quantite_disponible "
		            + "FROM Produit p "
		            + "LEFT JOIN Stock s ON p.id = s.id_produit "
		            + "WHERE IFNULL(s.quantite, 0) < p.quantite_min";

		    try (Connection conn = DatabaseConnection.connectToBDD();
		         PreparedStatement pstmt = conn.prepareStatement(query);
		         ResultSet rs = pstmt.executeQuery()) {

		        while (rs.next()) { 
		            produitsManquants.add(new Produit(
		                    rs.getInt("id"),
		                    rs.getString("nom"),
		                    rs.getString("description"),
		                    rs.getString("code_barre"),
		                    rs.getString("categorie"),
		                    rs.getTimestamp("date_creation") != null
		                            ? rs.getTimestamp("date_creation").toLocalDateTime()
		                            : null,
		                    rs.getInt("quantite_min") 
		            ));
		        }
		    }

		    return produitsManquants;
		}
}

package dao;

import model.FichierHistorique;
import service.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FichierHistoriqueDAO {

	private static final String INSERT_FICHIER = "INSERT INTO FichierHistorique (nom_fichier, status, erreurs) VALUES (?, ?, ?)";
	private static final String SELECT_ALL_FICHIERS = "SELECT * FROM FichierHistorique";
	private static final String DELETE_FICHIER_BY_ID = "DELETE FROM FichierHistorique WHERE id = ?";

	public void addFichier(FichierHistorique fichier) throws SQLException {
		try (Connection conn = DatabaseConnection.connectToBDD();
				PreparedStatement pstmt = conn.prepareStatement(INSERT_FICHIER)) {
			pstmt.setString(1, fichier.getNomFichier());
			pstmt.setString(2, fichier.getStatus());
			pstmt.setString(3, fichier.getErreurs());
			pstmt.executeUpdate();
		}
	}

	public List<FichierHistorique> getAllFichiers() throws SQLException {
		List<FichierHistorique> fichiers = new ArrayList<>();
		try (Connection conn = DatabaseConnection.connectToBDD();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SELECT_ALL_FICHIERS)) {
			while (rs.next()) {
				fichiers.add(new FichierHistorique(rs.getInt("id"), rs.getString("nom_fichier"),
						rs.getTimestamp("date_import").toLocalDateTime(),
						rs.getString("status"), rs.getString("erreurs")));
			}
		}
		return fichiers;
	}

	public void deleteFichierById(int id) throws SQLException {
		try (Connection conn = DatabaseConnection.connectToBDD();
				PreparedStatement pstmt = conn.prepareStatement(DELETE_FICHIER_BY_ID)) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		}
	}
}

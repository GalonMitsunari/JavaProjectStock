package dao;

import model.MouvementStock;
import service.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MouvementStockDAO {

	private static final String INSERT_MOUVEMENT = "INSERT INTO MouvementStock (id_produit, type_mouvement, quantite, id_rack) VALUES (?, ?, ?, ?)";
	private static final String SELECT_ALL_MOUVEMENTS = "SELECT * FROM MouvementStock";
	private static final String DELETE_MOUVEMENT_BY_ID = "DELETE FROM MouvementStock WHERE id = ?";

	public void addMouvement(MouvementStock mouvement) throws SQLException {
		try (Connection conn = DatabaseConnection.connectToBDD();
				PreparedStatement pstmt = conn.prepareStatement(INSERT_MOUVEMENT)) {
			pstmt.setInt(1, mouvement.getIdProduit());
			pstmt.setString(2, mouvement.getTypeMouvement());
			pstmt.setInt(3, mouvement.getQuantite());
			if (mouvement.getIdRack() != null) {
				pstmt.setInt(4, mouvement.getIdRack());
			} else {
				pstmt.setNull(4, Types.INTEGER);
			}
			pstmt.executeUpdate();
		}
	}

	public List<MouvementStock> getAllMouvements() throws SQLException {
		    List<MouvementStock> mouvements = new ArrayList<>();
		    try (Connection conn = DatabaseConnection.connectToBDD();
		         Statement stmt = conn.createStatement();
		         ResultSet rs = stmt.executeQuery(SELECT_ALL_MOUVEMENTS)) {

		        while (rs.next()) {
		            Integer idRack = (rs.getObject("id_rack") != null) ? rs.getInt("id_rack") : null; // Gestion manuelle de null
		            mouvements.add(new MouvementStock(
		                rs.getInt("id"),
		                rs.getInt("id_produit"),
		                rs.getString("type_mouvement"),
		                rs.getInt("quantite"),
		                idRack,
		                rs.getTimestamp("date_mouvement").toLocalDateTime()
		            ));
		        }
		    }
		    return mouvements;
		}


	public void deleteMouvementById(int id) throws SQLException {
		try (Connection conn = DatabaseConnection.connectToBDD();
				PreparedStatement pstmt = conn.prepareStatement(DELETE_MOUVEMENT_BY_ID)) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		}
	}
}
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

	/*public void addMouvement(MouvementStock mouvement) throws SQLException {
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
		
		String stockQuery;
		        if (mouvement.getTypeMouvement().equalsIgnoreCase("ENTREE")) {
		            stockQuery = "UPDATE Stock SET quantite = quantite + ? WHERE id_produit = ? AND id_rack = ?";
		        } else if (mouvement.getTypeMouvement().equalsIgnoreCase("SORTIE")) {
		            stockQuery = "UPDATE Stock SET quantite = quantite - ? WHERE id_produit = ? AND id_rack = ?";
		        } else {
		            throw new SQLException("Type de mouvement invalide");
		        }

		        stockStmt = conn.prepareStatement(stockQuery);
		        stockStmt.setInt(1, mouvement.getQuantite());
		        stockStmt.setInt(2, mouvement.getIdProduit());
		        stockStmt.setInt(3, mouvement.getIdRack());
		        stockStmt.executeUpdate();

		        conn.commit(); // Valide la transaction

		    } catch (SQLException e) {
		        if (conn != null) {
		            conn.rollback(); // Annule la transaction en cas d'erreur
		        }
		        e.printStackTrace();
		        throw e;
		    } finally {
		        if (mouvementStmt != null) mouvementStmt.close();
		        if (stockStmt != null) stockStmt.close();
		        if (conn != null) conn.close();
		    }
		*/

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

	public void addMouvement/*AndUpdateStock*/(MouvementStock mouvement) throws SQLException {
		try (Connection conn = DatabaseConnection.connectToBDD()) {
			conn.setAutoCommit(false); 
			String mouvementQuery = "INSERT INTO MouvementStock (id_produit, type_mouvement, quantite, id_rack) VALUES (?, ?, ?, ?)";
			try (PreparedStatement pstmt = conn.prepareStatement(mouvementQuery)) {
				pstmt.setInt(1, mouvement.getIdProduit());
				pstmt.setString(2, mouvement.getTypeMouvement());
				pstmt.setInt(3, mouvement.getQuantite());
				if (mouvement.getIdRack() != null) {
					pstmt.setInt(4, mouvement.getIdRack());
				} else {
					pstmt.setNull(4, Types.INTEGER);
				}
				pstmt.executeUpdate();

				String stockUpdateQuery;
				if (mouvement.getTypeMouvement().equalsIgnoreCase("ENTREE")) {
					stockUpdateQuery = "UPDATE Stock SET quantite = quantite + ? WHERE id_produit = ? AND id_rack = ?";
				} else if (mouvement.getTypeMouvement().equalsIgnoreCase("SORTIE")) {
					stockUpdateQuery = "UPDATE Stock SET quantite = quantite - ? WHERE id_produit = ? AND id_rack = ?";
				} else {
					throw new SQLException("Type de mouvement invalide : " + mouvement.getTypeMouvement());
				}

				try (PreparedStatement pstmt1 = conn.prepareStatement(stockUpdateQuery)) {
					pstmt1.setInt(1, mouvement.getQuantite());
					pstmt1.setInt(2, mouvement.getIdProduit());
					pstmt1.setInt(3, mouvement.getIdRack());
					pstmt1.executeUpdate();
				}

				conn.commit(); 
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}

	}
}
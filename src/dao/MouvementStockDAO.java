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

	public List<MouvementStock> getAllMouvements() throws SQLException {
		List<MouvementStock> mouvements = new ArrayList<>();
		try (Connection conn = DatabaseConnection.connectToBDD();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SELECT_ALL_MOUVEMENTS)) {

			while (rs.next()) {
				Integer idRack = (rs.getObject("id_rack") != null) ? rs.getInt("id_rack")
						: null;
				mouvements.add(new MouvementStock(rs.getInt("id"), rs.getInt("id_produit"),
						rs.getString("type_mouvement"), rs.getInt("quantite"),
						idRack,
						rs.getTimestamp("date_mouvement").toLocalDateTime()));
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

	public void addMouvement(MouvementStock mouvement) throws SQLException {
		    try (Connection conn = DatabaseConnection.connectToBDD()) {
		        conn.setAutoCommit(false);

		        // Requête pour insérer le mouvement
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

		            // Vérification de l'existence du stock pour ce produit et rack
		            String checkStockQuery = "SELECT quantite FROM Stock WHERE id_produit = ? AND id_rack = ?";
		            try (PreparedStatement pstmtCheckStock = conn.prepareStatement(checkStockQuery)) {
		                pstmtCheckStock.setInt(1, mouvement.getIdProduit());
		                pstmtCheckStock.setInt(2, mouvement.getIdRack());
		                try (ResultSet rs = pstmtCheckStock.executeQuery()) {
		                    if (rs.next()) {
		                        // Le stock existe, on met à jour
		                        int currentQuantity = rs.getInt("quantite");

		                        // Récupérer la capacité maximale du rack
		                        String getRackCapacityQuery = "SELECT capacite_max FROM Rack WHERE id = ?";
		                        try (PreparedStatement pstmtGetRackCapacity = conn.prepareStatement(getRackCapacityQuery)) {
		                            pstmtGetRackCapacity.setInt(1, mouvement.getIdRack());
		                            try (ResultSet rsRack = pstmtGetRackCapacity.executeQuery()) {
		                                if (rsRack.next()) {
		                                    int rackCapacity = rsRack.getInt("capacite_max");

		                                    // Vérifier si l'entrée dépasse la capacité maximale
		                                    int newQuantity = currentQuantity + (mouvement.getTypeMouvement().equalsIgnoreCase("ENTREE") ? mouvement.getQuantite() : -mouvement.getQuantite());
		                                    if (newQuantity > rackCapacity) {
		                                        throw new SQLException("La capacité maximale du rack est dépassée. Capacité max: " + rackCapacity);
		                                    }
		                                } else {
		                                    throw new SQLException("Le rack spécifié n'existe pas.");
		                                }
		                            }
		                        }

		                        // Si c'est un mouvement de sortie, vérifier la quantité disponible
		                        if (mouvement.getTypeMouvement().equalsIgnoreCase("SORTIE")) {
		                            if (currentQuantity < mouvement.getQuantite()) {
		                                throw new SQLException("Quantité de stock insuffisante pour la sortie.");
		                            }
		                            // Mise à jour du stock après sortie
		                            String updateStockQuery = "UPDATE Stock SET quantite = quantite - ? WHERE id_produit = ? AND id_rack = ?";
		                            try (PreparedStatement pstmtUpdateStock = conn.prepareStatement(updateStockQuery)) {
		                                pstmtUpdateStock.setInt(1, mouvement.getQuantite());
		                                pstmtUpdateStock.setInt(2, mouvement.getIdProduit());
		                                pstmtUpdateStock.setInt(3, mouvement.getIdRack());
		                                pstmtUpdateStock.executeUpdate();
		                            }
		                        } else {
		                            // Mise à jour du stock après entrée
		                            String updateStockQuery = "UPDATE Stock SET quantite = quantite + ? WHERE id_produit = ? AND id_rack = ?";
		                            try (PreparedStatement pstmtUpdateStock = conn.prepareStatement(updateStockQuery)) {
		                                pstmtUpdateStock.setInt(1, mouvement.getQuantite());
		                                pstmtUpdateStock.setInt(2, mouvement.getIdProduit());
		                                pstmtUpdateStock.setInt(3, mouvement.getIdRack());
		                                pstmtUpdateStock.executeUpdate();
		                            }
		                        }
		                    } else {
		                        // Le stock n'existe pas, on insère un nouvel enregistrement
		                        String insertStockQuery = "INSERT INTO Stock (id_produit, id_rack, quantite) VALUES (?, ?, ?)";
		                        try (PreparedStatement pstmtInsertStock = conn.prepareStatement(insertStockQuery)) {
		                            pstmtInsertStock.setInt(1, mouvement.getIdProduit());
		                            pstmtInsertStock.setInt(2, mouvement.getIdRack());
		                            pstmtInsertStock.setInt(3, mouvement.getQuantite());
		                            pstmtInsertStock.executeUpdate();
		                        }
		                    }
		                }
		            }

		            conn.commit();
		        } catch (SQLException e) {
		            e.printStackTrace();
		            conn.rollback();
		            throw e;
		        }
		    }
		}




}
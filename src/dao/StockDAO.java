package dao;

import model.Stock;
import service.DatabaseConnection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockDAO {
	public List<Stock> getAllStocks() throws SQLException {
		List<Stock> stocks = new ArrayList<>();
		String query = "SELECT * FROM Stock";

		try (Connection conn = DatabaseConnection.connectToBDD();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				stocks.add(new Stock(rs.getInt("id"), rs.getInt("id_produit"),
						rs.getInt("id_rack"), rs.getInt("quantite"),
						rs.getTimestamp("date_ajout")));
			}
		}
		return stocks;
	}

	public void addStock(Stock stock) throws SQLException {
		String query = "INSERT INTO Stock (id_produit, id_rack, quantite) VALUES (?, ?, ?)";
		try (Connection conn = DatabaseConnection.connectToBDD();
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, stock.getIdProduit());
			pstmt.setInt(2, stock.getIdRack());
			pstmt.setInt(3, stock.getQuantite());
			pstmt.executeUpdate();
		}
	}

	public void updateStock(Stock stock) throws SQLException {
		String query = "UPDATE Stock SET quantite = ? WHERE id = ?";
		try (Connection conn = DatabaseConnection.connectToBDD();
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, stock.getQuantite());
			pstmt.setInt(2, stock.getId());
			pstmt.executeUpdate();
		}
	}

	public void deleteStockById(int id) throws SQLException {
		String query = "DELETE FROM Stock WHERE id = ?";
		try (Connection conn = DatabaseConnection.connectToBDD();
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		}
	}

	public Stock getStockByProduitId(int idProduit) throws SQLException {
		String query = "SELECT s.*, p.nom AS produit_nom " + "FROM Stock s "
				+ "INNER JOIN Produit p ON s.id_produit = p.id " + "WHERE s.id_produit = ?";
		try (Connection conn = DatabaseConnection.connectToBDD();
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, idProduit);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					Stock stock = new Stock(rs.getInt("id"), rs.getInt("id_produit"),
							rs.getInt("id_rack"), rs.getInt("quantite"),
							rs.getTimestamp("date_ajout"));
					stock.setNomProduit(rs.getString("produit_nom"));
					return stock;
				}
			}
		}
		return null;
	}
	public void updateStocksFromCSV(String filePath) throws SQLException, IOException {
		    String query = "UPDATE Stock SET quantite = quantite + ? WHERE id_produit = ?";

		    try (BufferedReader br = new BufferedReader(new FileReader(filePath));
		         Connection conn = DatabaseConnection.connectToBDD();
		         PreparedStatement pstmt = conn.prepareStatement(query)) {

		        String line;
		        boolean isHeader = true;

		        while ((line = br.readLine()) != null) {
		            if (isHeader) { 
		                isHeader = false;
		                continue;
		            }
		            String[] data = line.split(",");
		            int idProduit = Integer.parseInt(data[0]);
		            int quantite = Integer.parseInt(data[1]);

		            pstmt.setInt(1, quantite);
		            pstmt.setInt(2, idProduit);
		            pstmt.executeUpdate();
		        }
		    }
		}


}

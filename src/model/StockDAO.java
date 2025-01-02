package model;

import service.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockDAO {
	public List<Stock> getAllStocks() throws SQLException {
		List<Stock> stocks = new ArrayList<>();
		String query = "SELECT * FROM stock";

		try (Connection conn = DatabaseConnection.connectToBDD();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				stocks.add(new Stock(rs.getInt("id"), rs.getString("name"),
						rs.getInt("quantity"), rs.getInt("category_id")));
			}
		}
		return stocks;
	}

	public void addStock(Stock stock) throws SQLException {
		String query = "INSERT INTO stock (name, quantity, category_id) VALUES (?, ?, ?)";
		try (Connection conn = DatabaseConnection.connectToBDD();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, stock.getName());
			pstmt.setInt(2, stock.getQuantity());
			pstmt.setInt(3, stock.getCategoryId());
			pstmt.executeUpdate();
		}
	}
}

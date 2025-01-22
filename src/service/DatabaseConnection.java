package service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/gestion_stock";
	private static final String USER = "root";
	private static final String PASSWORD = "";

	public static Connection connectToBDD() {
		try {
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("Connexion à la base de données établie.");
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erreur lors de la connexion à la base de données.", e);
		}
	}

	public static void close(Connection connection, Statement st) {
		try {
			if (st != null)
				st.close();
			if (connection != null && !connection.isClosed()) {
				connection.close();
				System.out.println("Connexion à la base de données fermée.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void resetTable(String tableName) throws SQLException {
		String query = "TRUNCATE TABLE " + tableName;
		try (Connection conn = connectToBDD(); Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(query);
			close(conn, stmt);
		}
	}

	public void deleteById(String tableName, int id) throws SQLException {
		String query = "DELETE FROM " + tableName + " WHERE id = ?";
		try (Connection conn = connectToBDD(); PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		}
	}

	public boolean entryExists(String tableName, int id) throws SQLException {
		String query = "SELECT 1 FROM " + tableName + " WHERE id = ?";
		try (Connection conn = connectToBDD(); PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			return rs.next();
		}
	}

	public void insertEntry(String tableName, Object... params) throws SQLException {
		StringBuilder query = new StringBuilder("INSERT INTO ").append(tableName).append(" VALUES (");
		for (int i = 0; i < params.length; i++) {
			query.append(i == 0 ? "?" : ", ?");
		}
		query.append(")");

		try (Connection conn = connectToBDD();
				PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i + 1, params[i]);
			}
			pstmt.executeUpdate();
		}
	}

	public List<Object[]> getAllRows(String tableName) throws SQLException {
		List<Object[]> rows = new ArrayList<>();
		String query = "SELECT * FROM " + tableName;

		try (Connection conn = connectToBDD();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();

			while (rs.next()) {
				Object[] row = new Object[columnCount];
				for (int i = 1; i <= columnCount; i++) {
					row[i - 1] = rs.getObject(i);
				}
				rows.add(row);
			}
		}
		return rows;
	}
}

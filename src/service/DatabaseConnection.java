package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/gestion_stock";
	private static final String USER = "root";
	private static final String PASSWORD = "";

	public static Connection connectToBDD() {
		try {
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException("Erreur lors de la connexion à la base de données", e);
		}
	}
}

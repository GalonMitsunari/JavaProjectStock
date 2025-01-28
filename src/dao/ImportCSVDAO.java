package dao;

import service.DatabaseConnection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImportCSVDAO {

	    public boolean importCSVToDatabase(String csvFilePath) {
	        String line;
	        String csvSplitBy = ",";

	        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
	             Connection conn = DatabaseConnection.connectToBDD()) {

	            String queryCheckExistence = "SELECT quantite FROM Stock WHERE id_produit = ? AND id_rack = ?";
	            String queryUpdateStock = "UPDATE Stock SET quantite = quantite + ? WHERE id_produit = ? AND id_rack = ?";
	            String queryInsertStock = "INSERT INTO Stock (id_produit, id_rack, quantite) VALUES (?, ?, ?)";

	            while ((line = br.readLine()) != null) {
	                String[] data = line.split(csvSplitBy);

	                if (data.length < 3) {
	                    System.out.println("Ligne ignorée : " + line);
	                    continue;
	                }

	                int idProduit = Integer.parseInt(data[0]);
	                int idRack = Integer.parseInt(data[1]);
	                int quantite = Integer.parseInt(data[2]);

	                try (PreparedStatement checkStmt = conn.prepareStatement(queryCheckExistence)) {
	                    checkStmt.setInt(1, idProduit);
	                    checkStmt.setInt(2, idRack);
	                    ResultSet rs = checkStmt.executeQuery();

	                    if (rs.next()) {
	                        try (PreparedStatement updateStmt = conn.prepareStatement(queryUpdateStock)) {
	                            updateStmt.setInt(1, quantite);
	                            updateStmt.setInt(2, idProduit);
	                            updateStmt.setInt(3, idRack);
	                            updateStmt.executeUpdate();
	                        }
	                    } else {
	                        try (PreparedStatement insertStmt = conn.prepareStatement(queryInsertStock)) {
	                            insertStmt.setInt(1, idProduit);
	                            insertStmt.setInt(2, idRack);
	                            insertStmt.setInt(3, quantite);
	                            insertStmt.executeUpdate();
	                        }
	                    }
	                }
	            }

	            return true;
	        } catch (IOException | SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	}


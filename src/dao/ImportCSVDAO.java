package dao;

import service.DatabaseConnection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImportCSVDAO {

    public boolean importCSVToDatabase(String csvFilePath) {
        String insertQuery = "INSERT INTO Stock (id_produit, id_rack, quantite) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.connectToBDD();
             BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length != 3) {
                    System.out.println("Ligne ignorée (mauvais format) : " + line);
                    continue;
                }

                try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                    pstmt.setInt(1, Integer.parseInt(values[0])); 
                    pstmt.setInt(2, Integer.parseInt(values[1])); 
                    pstmt.setInt(3, Integer.parseInt(values[2])); 
                    pstmt.executeUpdate();
                }
            }
            System.out.println("Importation réussie !");
            return true;
        } catch (IOException | SQLException | NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }
}

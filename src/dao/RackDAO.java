package dao;

import model.Rack;
import service.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RackDAO {
    private static final String INSERT_RACK = "INSERT INTO Rack (reference, capacite_max, description, emplacement) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_RACKS = "SELECT * FROM Rack";

    public void addRack(Rack rack) throws SQLException {
        try (Connection conn = DatabaseConnection.connectToBDD();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_RACK)) {
            pstmt.setString(1, rack.getReference());
            pstmt.setInt(2, rack.getCapaciteMax());
            pstmt.setString(3, rack.getDescription());
            pstmt.setString(4, rack.getEmplacement());
            pstmt.executeUpdate();
        }
    }

    public List<Rack> getAllRacks() throws SQLException {
        List<Rack> racks = new ArrayList<>();
        try (Connection conn = DatabaseConnection.connectToBDD();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_RACKS)) {
            while (rs.next()) {
                racks.add(new Rack(
                        rs.getInt("id"),
                        rs.getString("reference"),
                        rs.getInt("capacite_max"),
                        rs.getString("description"),
                        rs.getString("emplacement")
                ));
            }
        }
        return racks;
    }
}
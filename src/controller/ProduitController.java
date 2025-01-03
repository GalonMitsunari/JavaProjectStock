package controller;

import model.Produit;
import service.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import dao.ProduitDAO;

public class ProduitController {

    @FXML
    private ListView<String> produitListView;
    @FXML
    private TextField produitNomField;
    @FXML
    private TextField produitDescriptionField;
    @FXML
    private TextField produitCodeBarreField;
    @FXML
    private TextField produitCategorieField;

    private ProduitDAO produitDAO = new ProduitDAO();

    @FXML
    public void initialize() {
        loadProduits();
    }

    private void loadProduits() {
	    try {
	        produitListView.getItems().clear(); 
	        List<Produit> produits = produitDAO.getAllProduits(); 
	        for (Produit produit : produits) {
	            produitListView.getItems().add(produit.getNom() + " (" + produit.getCategorie() + ")");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

    @FXML
    public void addProduit(Produit produit) throws SQLException {
	    String query = "INSERT INTO Produit (nom, description, code_barre, categorie) VALUES (?, ?, ?, ?)";
	    try (Connection conn = DatabaseConnection.connectToBDD();
	         PreparedStatement pstmt = conn.prepareStatement(query)) {
	        pstmt.setString(1, produit.getNom());
	        pstmt.setString(2, produit.getDescription());
	        pstmt.setString(3, produit.getCodeBarre());
	        pstmt.setString(4, produit.getCategorie());
	        pstmt.executeUpdate();
	    }
	}
}

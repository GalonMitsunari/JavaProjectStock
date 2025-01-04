package controller;

import model.Produit;
import service.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    private void addProduit() {
        try {
            if (produitNomField.getText().isEmpty() || produitCodeBarreField.getText().isEmpty()) {
                showError("Erreur", "Veuillez remplir tous les champs obligatoires.");
                return;
            }

            Produit produit = new Produit(
                0, // L'ID sera généré automatiquement
                produitNomField.getText(),
                produitDescriptionField.getText(),
                produitCodeBarreField.getText(),
                produitCategorieField.getText(),
                null // La date sera gérée par MySQL
            );

            produitDAO.create(produit); // Utilise le DAO pour insérer
            loadProduits(); // Rafraîchit la liste
            clearFields(); // Réinitialise les champs
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Erreur", "Impossible d'ajouter le produit. Vérifiez les doublons sur le code-barre.");
        }
    }

    private void clearFields() {
        produitNomField.clear();
        produitDescriptionField.clear();
        produitCodeBarreField.clear();
        produitCategorieField.clear();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}

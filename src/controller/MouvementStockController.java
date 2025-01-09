package controller;

import dao.MouvementStockDAO;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import model.MouvementStock;

import java.sql.SQLException;
import java.time.LocalDateTime;

import Application.Main;

public class MouvementStockController {

    @FXML
    private ListView<String> mouvementListView;
    @FXML
    private TextField produitIdField;
    @FXML
    private ChoiceBox<String> typeMouvementChoiceBox;
    @FXML
    private TextField quantiteField;
    @FXML
    private TextField rackIdField;

    private MouvementStockDAO mouvementStockDAO = new MouvementStockDAO();

    @FXML
    public void initialize() {
        typeMouvementChoiceBox.getItems().addAll("ENTREE", "SORTIE");
        loadMouvements();
    }
    // Méthode pour retourner au menu principal
    @FXML
    private void goToMenu() {
        Main.changeScene("MenuView.fxml");
    }

    private void loadMouvements() {
        try {
            mouvementListView.getItems().clear();
            for (MouvementStock mouvement : mouvementStockDAO.getAllMouvements()) {
                mouvementListView.getItems().add(
                        "Produit ID: " + mouvement.getIdProduit() +
                        ", Type: " + mouvement.getTypeMouvement() +
                        ", Quantité: " + mouvement.getQuantite() +
                        ", Rack ID: " + (mouvement.getIdRack() != null ? mouvement.getIdRack() : "N/A") +
                        ", Date: " + mouvement.getDateMouvement()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addMouvement() {
        try {
            int idProduit = Integer.parseInt(produitIdField.getText());
            String typeMouvement = typeMouvementChoiceBox.getValue();
            int quantite = Integer.parseInt(quantiteField.getText());
            Integer idRack = rackIdField.getText().isEmpty() ? null : Integer.parseInt(rackIdField.getText());

            MouvementStock mouvement = new MouvementStock(
                    0,
                    idProduit,
                    typeMouvement,
                    quantite,
                    idRack,
                    LocalDateTime.now()
            );
            mouvementStockDAO.addMouvement(mouvement);
            loadMouvements();
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}

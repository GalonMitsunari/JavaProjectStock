package controller;

import dao.FichierHistoriqueDAO;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.ChoiceBox;
import model.FichierHistorique;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class FichierHistoriqueController {

    @FXML
    private ListView<String> fichierListView;
    @FXML
    private TextField fichierNomField;
    @FXML
    private ChoiceBox<String> statusChoiceBox;
    @FXML
    private TextArea erreursArea;

    private FichierHistoriqueDAO fichierHistoriqueDAO = new FichierHistoriqueDAO();

    @FXML
    public void initialize() {
        statusChoiceBox.getItems().addAll("SUCCES", "ERREUR");
        loadFichiers();
    }

    private void loadFichiers() {
        try {
            fichierListView.getItems().clear();
            for (FichierHistorique fichier : fichierHistoriqueDAO.getAllFichiers()) {
                fichierListView.getItems().add(
                        fichier.getNomFichier() + " (" + fichier.getStatus() + ") - " + fichier.getDateImport()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addFichier() {
        try {
            String nomFichier = fichierNomField.getText();
            String status = statusChoiceBox.getValue();
            String erreurs = erreursArea.getText();

            FichierHistorique fichier = new FichierHistorique(0, nomFichier, LocalDateTime.now(), status, erreurs);
            fichierHistoriqueDAO.addFichier(fichier);
            loadFichiers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteFichier() {
        try {
            String selectedItem = fichierListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                int id = Integer.parseInt(selectedItem.split(" ")[0]); // Assuming ID is the first word
                fichierHistoriqueDAO.deleteFichierById(id);
                loadFichiers();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

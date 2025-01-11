package controller;

import dao.RackDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Produit;
import model.Rack;

import java.sql.SQLException;

import Application.Main;

public class RackController {
    @FXML
    private ListView<String> rackListView;
    @FXML
    private TextField rackReferenceField;
    @FXML
    private TextField rackCapaciteMaxField;
    @FXML
    private TextField rackDescriptionField;
    @FXML
    private TextField rackEmplacementField;

    private RackDAO rackDAO = new RackDAO();

    @FXML
    public void initialize() {
        loadRacks();
    }
    // Méthode pour retourner au menu principal
    @FXML
    private void goToMenu() {
        Main.changeScene("MenuView.fxml");
    }
    private void loadRacks() {
        try {
            rackListView.getItems().clear();
            for (Rack rack : rackDAO.getAllRacks()) {
                rackListView.getItems().add(rack.getReference() + " - " + rack.getEmplacement());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addRack() {
        try {
            Rack rack = new Rack(
                    0,
                    rackReferenceField.getText(),
                    Integer.parseInt(rackCapaciteMaxField.getText()),
                    rackDescriptionField.getText(),
                    rackEmplacementField.getText()
            );
            rackDAO.addRack(rack);
            loadRacks();
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void updateRack() {
        try {
            String selectedRack = rackListView.getSelectionModel().getSelectedItem();
            if (selectedRack == null) {
                showError("Erreur", "Veuillez sélectionner un rack.");
                return;
            }

            selectedRack.setReference(rackReferenceField.getText());
            selectedRack.setCapaciteMax(Integer.parseInt(rackCapaciteMaxField.getText()));
            selectedRack.setDescription(rackDescriptionField.getText());
            selectedRack.setEmplacement(rackEmplacementField.getText());

            rackDAO.updateRack(selectedRack);

            loadRacks();
            clearFields();
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            showError("Erreur", "Impossible de mettre à jour le rack.");
        }
    }


    @FXML
    private void deleteRack() {
        try {
            int id = Integer.parseInt(rackReferenceField.getText());
            rackDAO.deleteRackById(id);
            loadRacks(); 
            clearFields();
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            showError("Erreur", "Impossible de supprimer le produit.");
        }
    }


    private void clearFields() {
        rackReferenceField.clear();
        rackCapaciteMaxField.clear();
        rackDescriptionField.clear();
        rackEmplacementField.clear();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

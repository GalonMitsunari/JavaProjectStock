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

	// M�thode pour retourner au menu principal
	@FXML
	private void goToMenu() {
		Main.changeScene("MenuView.fxml");
	}

	private void loadRacks() {
		try {
			rackListView.getItems().clear();
			for (Rack rack : rackDAO.getAllRacks()) {
				rackListView.getItems().add(rack.getId() + " : " + rack.getReference() + " - "
						+ rack.getCapaciteMax() + " - " + rack.getDescription()
						+ " - " + rack.getEmplacement());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void addRack() {
		try {
			Rack rack = new Rack(0, rackReferenceField.getText(),
					Integer.parseInt(rackCapaciteMaxField.getText()),
					rackDescriptionField.getText(), rackEmplacementField.getText());
			rackDAO.addRack(rack);
			loadRacks();
		} catch (SQLException | NumberFormatException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void updateRack() {
		try {
			String reference = rackReferenceField.getText();
			if (reference.isEmpty()) {
				showError("Erreur", "Veuillez saisir une r�f�rence pour identifier le rack.");
				return;
			}

			Rack rack = rackDAO.getRackByReference(reference);
			if (rack == null) {
				showError("Erreur", "Aucun rack trouv� avec la r�f�rence saisie.");
				return;
			}

			rack.setCapaciteMax(Integer.parseInt(rackCapaciteMaxField.getText()));
			rack.setDescription(rackDescriptionField.getText());
			rack.setEmplacement(rackEmplacementField.getText());

			rackDAO.updateRack(rack);

			loadRacks();
			clearFields();
		} catch (SQLException | NumberFormatException e) {
			e.printStackTrace();
			showError("Erreur", "Impossible de mettre � jour le rack.");
		}
	}

	@FXML
	private void deleteRack() {
		try {
			if (rackReferenceField.getText().isEmpty()) {
				showError("Erreur", "Veuillez saisir une r�f�rence de rack valide.");
				return;
			}

			int id;
			try {
				id = Integer.parseInt(rackReferenceField.getText());
			} catch (NumberFormatException e) {
				showError("Erreur", "La r�f�rence du rack doit �tre un num�ro valide.");
				return;
			}

			rackDAO.deleteRackById(id);

			loadRacks();
			clearFields();
		} catch (SQLException e) {
			e.printStackTrace();
			showError("Erreur", "Impossible de supprimer le rack.");
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

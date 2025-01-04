package controller;

import dao.RackDAO;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Rack;

import java.sql.SQLException;

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
}

package controller;

import model.Stock;
import model.StockDAO;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class StockController {
    @FXML
    private ListView<String> stockListView;
    @FXML
    private TextField stockNameField;
    @FXML
    private TextField stockQuantityField;

    private StockDAO stockDAO = new StockDAO();

    @FXML
    public void initialize() {
        loadStocks();
    }

    private void loadStocks() {
        try {
            stockListView.getItems().clear();
            for (Stock stock : stockDAO.getAllStocks()) {
                stockListView.getItems().add(stock.getName() + " (Quantité: " + stock.getQuantity() + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addStock() {
        try {
            String name = stockNameField.getText();
            int quantity = Integer.parseInt(stockQuantityField.getText());
            Stock newStock = new Stock(0, name, quantity, 1); // Catégorie par défaut
            stockDAO.addStock(newStock);
            loadStocks();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Quantité invalide.");
        }
    }
}

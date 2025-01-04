package controller;

import dao.StockDAO;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Stock;

import java.sql.SQLException;
import java.util.List;

public class StockController {

    @FXML
    private ListView<Stock> stockListView;
    @FXML
    private TextField stockIdProduitField;
    @FXML
    private TextField stockIdRackField;
    @FXML
    private TextField stockQuantiteField;

    private final StockDAO stockDAO = new StockDAO();

    @FXML
    public void initialize() {
        configureStockListView();
        loadStocks();
    }

    private void configureStockListView() {
	    stockListView.setCellFactory(param -> new ListCell<Stock>() {
		    @Override
		    protected void updateItem(Stock stock, boolean empty) {
		        super.updateItem(stock, empty);
		        if (empty || stock == null) {
		            setText(null);
		        } else {
		            setText("Produit ID: " + stock.getIdProduit() +
		                    ", Rack ID: " + stock.getIdRack() +
		                    ", Quantité: " + stock.getQuantite());
		        }
		    }
		});

    }


    private void loadStocks() {
        try {
            stockListView.getItems().clear();
            List<Stock> stocks = stockDAO.getAllStocks();
            stockListView.getItems().addAll(stocks);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void addStock() {
        try {
            int idProduit = Integer.parseInt(stockIdProduitField.getText());
            int idRack = Integer.parseInt(stockIdRackField.getText());
            int quantite = Integer.parseInt(stockQuantiteField.getText());

            Stock newStock = new Stock(0, idProduit, idRack, quantite, null);
            stockDAO.addStock(newStock);

            loadStocks();
            clearFields();
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateStock() {
        try {
            Stock selectedStock = stockListView.getSelectionModel().getSelectedItem();
            if (selectedStock == null) {
                return;
            }

            int quantite = Integer.parseInt(stockQuantiteField.getText());
            selectedStock.setQuantite(quantite);

            stockDAO.updateStock(selectedStock);
            loadStocks();
            clearFields();
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteStock() {
        try {
            Stock selectedStock = stockListView.getSelectionModel().getSelectedItem();
            if (selectedStock == null) {
                return;
            }

            stockDAO.deleteStockById(selectedStock.getId());
            loadStocks();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void displayStockDetails(MouseEvent event) {
        Stock selectedStock = stockListView.getSelectionModel().getSelectedItem();
        if (selectedStock != null) {
            stockIdProduitField.setText(String.valueOf(selectedStock.getIdProduit()));
            stockIdRackField.setText(String.valueOf(selectedStock.getIdRack()));
            stockQuantiteField.setText(String.valueOf(selectedStock.getQuantite()));
        }
    }

    private void clearFields() {
        stockIdProduitField.clear();
        stockIdRackField.clear();
        stockQuantiteField.clear();
    }
}

package controller;

import dao.StockDAO;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Stock;
import service.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Application.Main;

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
	
	@FXML
	private void goToMenu() {
		Main.changeScene("MenuView.fxml");
	}

	private void configureStockListView() {
		stockListView.setCellFactory(param -> new ListCell<Stock>() {
			@Override
			protected void updateItem(Stock stock, boolean empty) {
				super.updateItem(stock, empty);
				if (empty || stock == null) {
					setText(null);
				} else {
					setText("Produit ID: " + stock.getIdProduit() + ", Rack ID: "
							+ stock.getIdRack() + ", Quantité: "
							+ stock.getQuantite());
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
			if (!areFieldsValid()) {
				System.out.println("Veuillez remplir tous les champs.");
				return;
			}

			int idProduit = Integer.parseInt(stockIdProduitField.getText());
			int idRack = Integer.parseInt(stockIdRackField.getText());
			int quantite = Integer.parseInt(stockQuantiteField.getText());

			if (!doesProduitExist(idProduit)) {
				System.out.println("Produit avec l'ID " + idProduit + " n'existe pas.");
				return;
			}

			if (!doesRackExist(idRack)) {
				System.out.println("Rack avec l'ID " + idRack + " n'existe pas.");
				return;
			}

			Stock newStock = new Stock(idProduit, idRack, quantite);
			stockDAO.addStock(newStock);

			loadStocks();
			clearFields();
		} catch (NumberFormatException e) {
			System.out.println("Veuillez entrer des valeurs numériques valides.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Erreur SQL lors de l'ajout du stock.");
			e.printStackTrace();
		}
	}

	@FXML
	private void updateStock() {
		try {
			Stock selectedStock = stockListView.getSelectionModel().getSelectedItem();
			if (selectedStock == null) {
				System.err.println("Aucun stock sélectionné !");
				return;
			}

			if (areFieldsValid()) {
				int quantite = Integer.parseInt(stockQuantiteField.getText());
				selectedStock.setQuantite(quantite);

				stockDAO.updateStock(selectedStock);
				loadStocks();
				clearFields();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void deleteStock() {
		try {
			Stock selectedStock = stockListView.getSelectionModel().getSelectedItem();
			if (selectedStock == null) {
				System.err.println("Aucun stock sélectionné !");
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

	private boolean areFieldsValid() {
		if (stockIdProduitField.getText().isEmpty() || stockIdRackField.getText().isEmpty()
				|| stockQuantiteField.getText().isEmpty()) {
			System.err.println("Tous les champs doivent être remplis !");
			return false;
		}

		try {
			Integer.parseInt(stockIdProduitField.getText());
			Integer.parseInt(stockIdRackField.getText());
			Integer.parseInt(stockQuantiteField.getText());
		} catch (NumberFormatException e) {
			System.err.println("Les champs ID Produit, ID Rack et Quantité doivent être des nombres !");
			return false;
		}

		return true;
	}

	private boolean doesProduitExist(int idProduit) {
		try (Connection conn = DatabaseConnection.connectToBDD();
				java.sql.PreparedStatement pstmt = conn
						.prepareStatement("SELECT 1 FROM Produit WHERE id = ?")) {
			pstmt.setInt(1, idProduit);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean doesRackExist(int idRack) {
		try (Connection conn = DatabaseConnection.connectToBDD();
				java.sql.PreparedStatement pstmt = conn
						.prepareStatement("SELECT 1 FROM Rack WHERE id = ?")) {
			pstmt.setInt(1, idRack);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}

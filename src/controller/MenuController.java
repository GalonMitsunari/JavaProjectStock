package controller;

import Application.Main;
import javafx.fxml.FXML;

public class MenuController {
	    @FXML
	    public void goToProduitView() {
	        Main.changeScene("ProduitView.fxml", "Gestion des Produits");
	    }

	    @FXML
	    public void goToRackView() {
	        Main.changeScene("RackView.fxml", "Gestion des Racks");
	    }

	    @FXML
	    public void goToStockView() {
	        Main.changeScene("StockView.fxml", "Gestion des Stocks");
	    }

	    @FXML
	    public void goToMouvementView() {
	        Main.changeScene("MouvementStockView.fxml", "Gestion des Mouvements de Stock");
	    }
	}


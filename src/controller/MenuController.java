package controller;

import Application.Main;
import javafx.fxml.FXML;

public class MenuController {
	    @FXML
	    public void goToProduitView() {
	        Main.changeScene("ProduitView.fxml");
	    }

	    @FXML
	    public void goToRackView() {
	        Main.changeScene("RackView.fxml");
	    }

	    @FXML
	    public void goToStockView() {
	        Main.changeScene("StockView.fxml");
	    }

	    @FXML
	    public void goToMouvementView() {
	        Main.changeScene("MouvementStockView.fxml");
	    }
	}


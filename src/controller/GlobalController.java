package controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Produit;
import model.Rack;
import model.Stock;
import service.FTPService;
import model.FichierHistorique;
import model.MouvementStock;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dao.ImportCSVDAO;
import dao.MouvementStockDAO;
import dao.ProduitDAO;
import dao.RackDAO;
import dao.StockDAO;

public class GlobalController {

	@FXML
	private ListView<Produit> produitListView;
	@FXML
	private TextField produitIdField, produitNomField, produitDescriptionField, produitCodeBarreField,
			produitCategorieField;

	@FXML
	private ListView<Rack> rackListView;
	@FXML
	private TextField rackIdField, rackReferenceField, rackCapaciteMaxField, rackDescriptionField,
			rackEmplacementField;

	@FXML
	private ListView<MouvementStock> mouvementListView;
	@FXML
	private TextField mouvementProduitField, mouvementQuantiteField, mouvementRackField;

	@FXML
	private TableView<MouvementStock> historiqueTableView;
	@FXML
	private ListView<String> stockListView;
	private TextField produitColumn, quantiteColumn;


	private final ProduitDAO produitDAO = new ProduitDAO();
	private final RackDAO rackDAO = new RackDAO();
	private final MouvementStockDAO mouvementStockDAO = new MouvementStockDAO();
	private final StockDAO stockDAO = new StockDAO();

	@FXML
	public void initialize() {
		loadProduits();
		loadRacks();
		loadMouvements();
		loadStocks();
	}

	private void loadProduits() {
		try {
			produitListView.getItems().setAll(produitDAO.getAllProduits());
		} catch (SQLException e) {
			showError("Erreur", "Impossible de charger les produits.");
		}
	}

	private void loadRacks() {
		try {
			rackListView.getItems().setAll(rackDAO.getAllRacks());
		} catch (SQLException e) {
			showError("Erreur", "Impossible de charger les racks.");
		}
	}

	private void loadMouvements() {
		try {
			mouvementListView.getItems().setAll(mouvementStockDAO.getAllMouvements());
		} catch (SQLException e) {
			showError("Erreur", "Impossible de charger les mouvements.");
		}
	}
	@FXML
	private void loadStocks() {
	    try {
	        List<Stock> stocks = stockDAO.getAllStocks();
	        List<String> stockDisplayList = new ArrayList<>();

	        for (Stock stock : stocks) {
	            Produit produit = produitDAO.readProduitById(stock.getIdProduit());
	            String produitNom = (produit != null) ? produit.getNom() : "Produit inconnu";
	            String stockInfo = "Produit : " + produitNom + 
	                               ", Quantité : " + stock.getQuantite();
	            stockDisplayList.add(stockInfo);
	        }

	        stockListView.getItems().setAll(stockDisplayList);

	    } catch (SQLException e) {
	        e.printStackTrace();
	        showError("Erreur", "Impossible de charger les stocks.");
	    }
	}



	@FXML
	private void addProduit() {
		try {
			Produit produit = new Produit(0, produitNomField.getText(), null,
					produitCodeBarreField.getText(), produitCategorieField.getText(),
					null);
			produitDAO.create(produit);
			loadProduits();
		} catch (SQLException e) {
			e.printStackTrace();
			showError("Erreur", "Impossible d'ajouter le produit. Possibilité d'un Code Barre en doublon.");
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
			showError("Erreur", "Impossible d'ajouter le rack.");
		}
	}

	@FXML
	private void addMouvementEntree() {
	    if (mouvementProduitField == null || mouvementProduitField.getText() == null || mouvementProduitField.getText().isEmpty()) {
	        showError("Erreur", "Le champ Produit ID est vide.");
	        return;
	    }
	    if (mouvementQuantiteField == null || mouvementQuantiteField.getText() == null || mouvementQuantiteField.getText().isEmpty()) {
	        showError("Erreur", "Le champ Quantité est vide.");
	        return;
	    }

	    try {
	        int produitId = Integer.parseInt(mouvementProduitField.getText());
	        int quantite = Integer.parseInt(mouvementQuantiteField.getText());

	        Integer rackId = null;
	        if (mouvementRackField != null && mouvementRackField.getText() != null && !mouvementRackField.getText().isEmpty()) {
	            rackId = Integer.parseInt(mouvementRackField.getText());
	        }

	        if (produitId <= 0 || quantite <= 0) {
	            showError("Erreur", "Produit ID ou quantité invalide.");
	            return;
	        }

	        MouvementStock mouvement = new MouvementStock(0, produitId, "ENTREE", quantite, rackId, LocalDateTime.now()); 

	        mouvementStockDAO.addMouvement(mouvement);

	        loadMouvements(); 
	        loadStocks();
	    } catch (NumberFormatException e) {
	        showError("Erreur", "Veuillez entrer des valeurs numériques valides.");
	    } catch (SQLException e) {
	        e.printStackTrace();
	        showError("Erreur SQL", "Erreur lors de l'ajout du mouvement d'entrée.");
	    }
	}



	@FXML
	private void addMouvementSortie() {
		    if (mouvementProduitField == null || mouvementProduitField.getText() == null || mouvementProduitField.getText().isEmpty()) {
		        showError("Erreur", "Le champ Produit ID est vide.");
		        return;
		    }
		    if (mouvementQuantiteField == null || mouvementQuantiteField.getText() == null || mouvementQuantiteField.getText().isEmpty()) {
		        showError("Erreur", "Le champ Quantité est vide.");
		        return;
		    }

		    try {
		        int produitId = Integer.parseInt(mouvementProduitField.getText());
		        int quantite = Integer.parseInt(mouvementQuantiteField.getText());

		        Integer rackId = null;
		        if (mouvementRackField != null && mouvementRackField.getText() != null && !mouvementRackField.getText().isEmpty()) {
		            rackId = Integer.parseInt(mouvementRackField.getText());
		        }

		        if (produitId <= 0 || quantite <= 0) {
		            showError("Erreur", "Produit ID ou quantité invalide.");
		            return;
		        }

		        MouvementStock mouvement = new MouvementStock(0, produitId, "SORTIE", quantite, rackId, LocalDateTime.now()); 

		        mouvementStockDAO.addMouvement(mouvement);

		        loadMouvements();
		        loadStocks();
		    } catch (NumberFormatException e) {
		        showError("Erreur", "Veuillez entrer des valeurs numériques valides.");
		    } catch (SQLException e) {
		        e.printStackTrace();
		        showError("Erreur SQL", "Erreur lors de l'ajout du mouvement de sortie.");
		    }
	}
	@FXML
	private void updateStock() {
	    if (mouvementProduitField == null || mouvementProduitField.getText().isEmpty()) {
	        showError("Erreur", "Le champ Produit ID est vide.");
	        return;
	    }
	    if (mouvementQuantiteField == null || mouvementQuantiteField.getText().isEmpty()) {
	        showError("Erreur", "Le champ Quantité est vide.");
	        return;
	    }

	    try {
	        int produitId = Integer.parseInt(mouvementProduitField.getText());
	        int quantite = Integer.parseInt(mouvementQuantiteField.getText());

	        Stock stock = stockDAO.getStockByProduitId(produitId);
	        if (stock == null) {
	            showError("Erreur", "Le stock pour ce produit n'existe pas.");
	            return;
	        }
	        stock.setQuantite(quantite);
	        stockDAO.updateStock(stock);

	        loadStocks(); 
	    } catch (NumberFormatException e) {
	        showError("Erreur", "Veuillez entrer des valeurs numériques valides.");
	    } catch (SQLException e) {
	        e.printStackTrace();
	        showError("Erreur SQL", "Erreur lors de la mise à jour du stock.");
	    }
	}
	
	@FXML
	private void handleFTPImport() {
	    FTPService ftpService = new FTPService();
	    ImportCSVDAO importCSVDAO = new ImportCSVDAO();

	    String remoteFile = "/CCI/Stock.csv";
	    String localFile = "C:/Users/Eleve/Downloads/Stock.csv"; 

	    String downloadResult = ftpService.downloadCSV(remoteFile, localFile);
	    if (!downloadResult.contains("succès")) {
	        showError("Erreur FTP", "Impossible de télécharger le fichier.");
	        return;
	    }

	    boolean importSuccess = importCSVDAO.importCSVToDatabase(localFile);


	    if (importSuccess) {
	        showInfo("Succès", "Le fichier a été importé avec succès dans la base de données.");
	        loadStocks();
	    } else {
	        showError("Erreur", "L'importation du fichier dans la base de données a échoué.");
	    }
	}

	private void showInfo(String title, String message) {
	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setTitle(title);
	    alert.setContentText(message);
	    alert.showAndWait();
	}


	private void showError(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}
}

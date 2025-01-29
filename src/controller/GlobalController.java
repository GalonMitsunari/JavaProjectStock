package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import dao.ImportCSVDAO;
import dao.MouvementStockDAO;
import dao.ProduitDAO;
import dao.RackDAO;
import dao.StockDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.MouvementStock;
import model.Produit;
import model.Rack;
import model.Stock;
import service.FTPService;
import utils.CSVGenerator;
import utils.CSVReaderUtil;

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
	private List<String[]> manquantsPourAchat = new ArrayList<>();
	private static final Logger logger = Logger.getLogger(GlobalController.class.getName());


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
				String produitDescription = (produit != null) ? produit.getDescription() : "Produit inconnu";
				String stockInfo = "Produit : " + produitNom + " " + produitDescription + ", Quantité : "
						+ stock.getQuantite();
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
					null, 0);
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
		if (mouvementProduitField == null || mouvementProduitField.getText() == null
				|| mouvementProduitField.getText().isEmpty()) {
			showError("Erreur", "Le champ Produit ID est vide.");
			return;
		}
		if (mouvementQuantiteField == null || mouvementQuantiteField.getText() == null
				|| mouvementQuantiteField.getText().isEmpty()) {
			showError("Erreur", "Le champ Quantité est vide.");
			return;
		}

		try {
			int produitId = Integer.parseInt(mouvementProduitField.getText());
			int quantite = Integer.parseInt(mouvementQuantiteField.getText());

			Integer rackId = null;
			if (mouvementRackField != null && mouvementRackField.getText() != null
					&& !mouvementRackField.getText().isEmpty()) {
				rackId = Integer.parseInt(mouvementRackField.getText());
			}

			if (produitId <= 0 || quantite <= 0) {
				showError("Erreur", "Produit ID ou quantité invalide.");
				return;
			}

			MouvementStock mouvement = new MouvementStock(0, produitId, "ENTREE", quantite, rackId,
					LocalDateTime.now());

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
		if (mouvementProduitField == null || mouvementProduitField.getText() == null
				|| mouvementProduitField.getText().isEmpty()) {
			showError("Erreur", "Le champ Produit ID est vide.");
			return;
		}
		if (mouvementQuantiteField == null || mouvementQuantiteField.getText() == null
				|| mouvementQuantiteField.getText().isEmpty()) {
			showError("Erreur", "Le champ Quantité est vide.");
			return;
		}

		try {
			int produitId = Integer.parseInt(mouvementProduitField.getText());
			int quantite = Integer.parseInt(mouvementQuantiteField.getText());

			Integer rackId = null;
			if (mouvementRackField != null && mouvementRackField.getText() != null
					&& !mouvementRackField.getText().isEmpty()) {
				rackId = Integer.parseInt(mouvementRackField.getText());
			}

			if (produitId <= 0 || quantite <= 0) {
				showError("Erreur", "Produit ID ou quantité invalide.");
				return;
			}

			MouvementStock mouvement = new MouvementStock(0, produitId, "SORTIE", quantite, rackId,
					LocalDateTime.now());

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
		    if (downloadResult.contains("Erreur")) {
		        showError("Erreur FTP", downloadResult);
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

	    private void showError(String title, String message) {
		        logger.severe("Erreur: " + message);
		        Alert alert = new Alert(Alert.AlertType.ERROR);
		        alert.setTitle(title);
		        alert.setHeaderText("Détails de l'erreur");
		        alert.setContentText(message);
		        alert.showAndWait();
		    }

		    private void showInfo(String title, String message) {
		        logger.info("Info: " + message);
		        Alert alert = new Alert(Alert.AlertType.INFORMATION);
		        alert.setTitle(title);
		        alert.setHeaderText(null);
		        alert.setContentText(message);
		        alert.showAndWait();
		    }


	@FXML
	private void importFabNeeds() {
	    FTPService ftpService = new FTPService();
	    String remoteFile = "/Fab/Besoins.csv";
	    String localFile = "C:/Users/Eleve/Downloads/Besoins.csv";

	    String downloadResult = ftpService.downloadCSV(remoteFile, localFile);
	    if (!downloadResult.contains("succès")) {
	        showError("Erreur FTP", "Impossible de télécharger les besoins de Fab.");
	        return;
	    }

	    try {
	        List<String[]> besoinsFab = CSVReaderUtil.readCSV(localFile);

	        manquantsPourAchat.clear(); 

	        for (String[] besoin : besoinsFab) {
	            int idProduit = Integer.parseInt(besoin[0]);
	            int quantiteDemandee = Integer.parseInt(besoin[1]);

	            Stock stock = stockDAO.getStockByProduitId(idProduit);

	            if (stock == null || stock.getQuantite() < quantiteDemandee) {
	                int quantiteManquante = quantiteDemandee - (stock != null ? stock.getQuantite() : 0);
	                manquantsPourAchat.add(new String[]{String.valueOf(idProduit), String.valueOf(quantiteManquante)});
	            }
	        }

	        if (manquantsPourAchat.isEmpty()) {
	            showInfo("Stocks suffisants", "Tous les besoins de Fab sont couverts.");
	            generateConfirmationForFab();
	        } else {
	            exportMissingStocksForAchat();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        showError("Erreur Fichier", "Erreur lors de l'importation des besoins de Fab.");
	    }
	}

	    @FXML
	    private void exportMissingStocksForAchat() {
	        try {
	            String filePath = "C:/Users/Eleve/Downloads/stocks_manquants_achat.csv";
	            String[] header = {"id_produit", "quantite_manquante"};
	            CSVGenerator.generateCSV(filePath, manquantsPourAchat, header);

	            showInfo("Fichier généré", "Le fichier des stocks manquants à envoyer à Achat a été généré :\n" + filePath);
	        } catch (IOException e) {
	            e.printStackTrace();
	            showError("Erreur IO", "Erreur lors de la génération du fichier CSV pour Achat.");
	        }
	    }


	    @FXML
	    private void importStocksFromAchat() {
	        FTPService ftpService = new FTPService();
	        String remoteFile = "/Achat/StocksRecus.csv";
	        String localFile = "C:/Users/Eleve/Downloads/StocksRecus.csv";

	        String downloadResult = ftpService.downloadCSV(remoteFile, localFile);
	        if (!downloadResult.contains("succès")) {
	            showError("Erreur FTP", "Impossible de télécharger les stocks reçus d'Achat.");
	            return;
	        }

	        try {
	            List<String[]> stocksRecus = CSVReaderUtil.readCSV(localFile); 

	            for (String[] stockRecu : stocksRecus) {
	                int idProduit = Integer.parseInt(stockRecu[0]);
	                int quantiteAjoutee = Integer.parseInt(stockRecu[1]);

	                Stock stock = stockDAO.getStockByProduitId(idProduit);
	                if (stock != null) {
	                    stock.setQuantite(stock.getQuantite() + quantiteAjoutee);
	                    stockDAO.updateStock(stock);
	                } else {
	                    Stock newStock = new Stock(idProduit, 1, quantiteAjoutee);
	                    stockDAO.addStock(newStock);
	                }
	            }

	            showInfo("Importation réussie", "Les stocks reçus d'Achat ont été importés avec succès.");
	            loadStocks();
	            generateConfirmationForFab();
	        } catch (Exception e) {
	            e.printStackTrace();
	            showError("Erreur Fichier", "Erreur lors de l'importation des stocks reçus d'Achat.");
	        }
	    }

	    @FXML
	    private void generateConfirmationForFab() {
	        try {
	            List<String[]> stocksRecus = getStocksRecusFromDatabase(); 

	            if (stocksRecus.isEmpty()) {
	                showInfo("Aucune confirmation", "Aucun stock reçu à confirmer pour Fab.");
	                return;
	            }

	            String filePath = "C:/Users/Eleve/Downloads/confirmation_fab.csv";
	            String[] header = {"id_produit", "quantite_recue"};

	            CSVGenerator.generateCSV(filePath, stocksRecus, header);

	            showInfo("Fichier généré", "La confirmation pour Fab a été générée :\n" + filePath);
	        } catch (IOException e) {
	            e.printStackTrace();
	            showError("Erreur IO", "Erreur lors de la génération du fichier de confirmation pour Fab.");
	        } catch (SQLException e) {
	            e.printStackTrace();
	            showError("Erreur SQL", "Impossible de récupérer les stocks reçus depuis la base de données.");
	        }
	    }

	    private List<String[]> getStocksRecusFromDatabase() throws SQLException {
	        List<String[]> stocksRecus = new ArrayList<>();

	        List<Stock> stocks = stockDAO.getAllStocks(); 
	        for (Stock stock : stocks) {
	            if (stock.getQuantite() > 0) { 
	                stocksRecus.add(new String[]{
	                    String.valueOf(stock.getIdProduit()),
	                    String.valueOf(stock.getQuantite())
	                });
	            }
	        }

	        return stocksRecus;
	    }

	}
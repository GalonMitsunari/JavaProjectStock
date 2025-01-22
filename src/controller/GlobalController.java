package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Produit;
import model.Rack;
import model.MouvementStock;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import dao.MouvementStockDAO;
import dao.ProduitDAO;
import dao.RackDAO;
import dao.StockDAO;

public class GlobalController {

	// Produits
	@FXML
	private ListView<Produit> produitListView;
	@FXML
	private TextField produitIdField, produitNomField, produitDescriptionField, produitCodeBarreField,
			produitCategorieField;

	// Racks
	@FXML
	private ListView<Rack> rackListView;
	@FXML
	private TextField rackIdField, rackReferenceField, rackCapaciteMaxField, rackDescriptionField,
			rackEmplacementField;

	// Mouvements
	@FXML
	private ListView<MouvementStock> mouvementListView;
	@FXML
	private TextField mouvementProduitField, mouvementQuantiteField, mouvementRackField;

	// Historique
	@FXML
	private TableView<MouvementStock> historiqueTableView;

	private final ProduitDAO produitDAO = new ProduitDAO();
	private final RackDAO rackDAO = new RackDAO();
	private final MouvementStockDAO mouvementStockDAO = new MouvementStockDAO();
	private final StockDAO stockDAO = new StockDAO();

	@FXML
	public void initialize() {
		loadProduits();
		loadRacks();
		loadMouvements();
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
	    // Vérification de l'initialisation des champs
	    if (mouvementProduitField == null || mouvementProduitField.getText() == null || mouvementProduitField.getText().isEmpty()) {
	        showError("Erreur", "Le champ Produit ID est vide.");
	        return;
	    }
	    if (mouvementQuantiteField == null || mouvementQuantiteField.getText() == null || mouvementQuantiteField.getText().isEmpty()) {
	        showError("Erreur", "Le champ Quantité est vide.");
	        return;
	    }

	    try {
	        // Récupérer les valeurs correctement
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

	        // Affichage pour débogage
	        System.out.println("Produit ID: " + produitId);
	        System.out.println("Quantité: " + quantite);
	        System.out.println("Rack ID: " + rackId);

	        // Créer l'objet MouvementStock
	        MouvementStock mouvement = new MouvementStock(0, produitId, "ENTREE", quantite, rackId, LocalDateTime.now()); 

	        // Enregistrement du mouvement dans la base de données
	        mouvementStockDAO.addMouvement(mouvement);

	        // Rafraîchissement de la ListView des mouvements
	        loadMouvements(); // Recharger les mouvements après ajout
	    } catch (NumberFormatException e) {
	        showError("Erreur", "Veuillez entrer des valeurs numériques valides.");
	    } catch (SQLException e) {
	        e.printStackTrace();
	        showError("Erreur SQL", "Erreur lors de l'ajout du mouvement d'entrée.");
	    }
	}



	@FXML
	private void addMouvementSortie() {
		    // Vérification de l'initialisation des champs
		    if (mouvementProduitField == null || mouvementProduitField.getText() == null || mouvementProduitField.getText().isEmpty()) {
		        showError("Erreur", "Le champ Produit ID est vide.");
		        return;
		    }
		    if (mouvementQuantiteField == null || mouvementQuantiteField.getText() == null || mouvementQuantiteField.getText().isEmpty()) {
		        showError("Erreur", "Le champ Quantité est vide.");
		        return;
		    }

		    try {
		        // Récupérer les valeurs correctement
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

		        // Affichage pour débogage
		        System.out.println("Produit ID: " + produitId);
		        System.out.println("Quantité: " + quantite);
		        System.out.println("Rack ID: " + rackId);

		        // Créer l'objet MouvementStock
		        MouvementStock mouvement = new MouvementStock(0, produitId, "SORTIE", quantite, rackId, LocalDateTime.now()); 

		        // Enregistrement du mouvement dans la base de données
		        mouvementStockDAO.addMouvement(mouvement);

		        // Rafraîchissement de la ListView des mouvements
		        loadMouvements(); // Recharger les mouvements après ajout
		    } catch (NumberFormatException e) {
		        showError("Erreur", "Veuillez entrer des valeurs numériques valides.");
		    } catch (SQLException e) {
		        e.printStackTrace();
		        showError("Erreur SQL", "Erreur lors de l'ajout du mouvement de sortie.");
		    }
	}

	private void showError(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}
}

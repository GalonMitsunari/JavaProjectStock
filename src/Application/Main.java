package Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        changeScene("ProduitView.fxml", "Gestion des Produits - Stocks");
    }

    /**
     * Méthode utilitaire pour changer dynamiquement la scène.
     *
     * @param fxmlFile Le fichier FXML à charger.
     * @param title    Le titre de la fenêtre.
     */
    public static void changeScene(String fxmlFile, String title) {
        try {
            VBox root = FXMLLoader.load(Main.class.getResource("/view/" + fxmlFile));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle(title);
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de la vue " + fxmlFile + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

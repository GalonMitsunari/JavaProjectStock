package Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;
        changeScene("MenuView.fxml", "Menu Principal");
    }

    public static void changeScene(String fxmlFile, String title) {
	    try {
	        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/" + fxmlFile));
	        VBox root = loader.load();
	        Scene scene = new Scene(root);
	        primaryStage.setScene(scene);
	        primaryStage.setTitle(title);
	        primaryStage.show();
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.err.println("Erreur lors du chargement de la vue : " + fxmlFile);
	    }
	}


    public static void main(String[] args) {
        launch(args);
    }
}

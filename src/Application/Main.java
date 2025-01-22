package Application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	private static Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		Main.primaryStage = primaryStage;
		changeScene("MenuView.fxml");
	}

	public static void changeScene(String fxml) {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/" + fxml));
			Scene scene = new Scene(loader.load());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Erreur lors du chargement de la vue : " + fxml);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}

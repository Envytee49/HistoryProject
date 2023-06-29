package app;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	public static final String TOP_LEVEL_SCREEN_PATH = "/app/View/MainScreen.fxml";
	
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(URLCreator.createURLFromPath(TOP_LEVEL_SCREEN_PATH));
		Scene scene = new Scene(root);
        stage.setTitle("History Application");
        stage.setScene(scene);
        stage.show();
	}
	
	public static void main(String[] args) {
		App.launch(args);
	}

}

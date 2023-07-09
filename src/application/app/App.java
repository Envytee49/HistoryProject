package application.app;

import database.Eras;
import database.Events;
import database.Festivals;
import database.HistoricalFigures;
import database.Sites;
import helper.URLCreator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import model.Era;

import java.net.URL;
import java.nio.file.FileSystems;


public class App extends Application{
	
	public static final String TOP_LEVEL_SCREEN_PATH = "/application/view/MainScreen.fxml";
	
	
	@Override
	public void start(Stage stage) throws Exception {
		
		try {
			
			Parent root = FXMLLoader.load(URLCreator.createURLFromPath(TOP_LEVEL_SCREEN_PATH));
			Scene scene = new Scene(root);
	        stage.setTitle("History Application");
	        stage.setScene(scene);
	        stage.setResizable(false);
	        stage.setFullScreen(true);
	        stage.show();
	        
	        Eras eras = new Eras();
	        Events events = new Events();
	        Festivals festivals = new Festivals();
	        Sites sites = new Sites();
	        HistoricalFigures figures = new HistoricalFigures();
	        
	        eras.queryJSON();
	        events.queryJSON();
	        festivals.queryJSON();
	        sites.queryJSON();
	        figures.queryJSON();
	        
	        
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		App.launch(args);
	}


}

package application.controller;

import java.io.IOException;

import application.app.URLCreator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

public class SideBarController {
	public static final String VIEW_PATH = "/application/view";
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void switchScene(ActionEvent event, String path) {
		try {
			root = FXMLLoader.load(URLCreator.createURLFromPath(path));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
    void switchToEra(ActionEvent event) {
		switchScene(event,VIEW_PATH+"/Era.fxml");
    }

    @FXML
    void switchToEvent(ActionEvent event) {
    	switchScene(event,VIEW_PATH+"/Event.fxml");
    }

    @FXML
    void switchToFes(ActionEvent event) {
    	switchScene(event,VIEW_PATH+"/Festival.fxml");
    }

    @FXML
    void switchToFig(ActionEvent event) {
    	switchScene(event,VIEW_PATH+"/Figure.fxml");
    }

    @FXML
    void switchToMain(ActionEvent event) {
    	switchScene(event,VIEW_PATH+"/MainScreen.fxml");
    }

    @FXML
    void switchToSite(ActionEvent event) {
    	switchScene(event,VIEW_PATH+"/Site.fxml");
    }
}

package application.controller;

import java.io.IOException;
import java.util.Map;

import database.HistoricalFigures;
import helper.URLCreator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.HistoricalFigure;
import model.Site;

public class SiteDetailController {
	@FXML
    private Text nameText;

    @FXML
    private Text locationText;

    @FXML
    private Text constructionDateText;

    @FXML
    private Text noteText;

    @FXML
    private Text categoryText;

    @FXML
    private Text approvedText;

    //@FXML
    //private Text festivalsText;

    @FXML
    private FlowPane relatedCharsFlowPane;

    @FXML
    private SideBarController sideBarController;

    private Site site;

    @FXML
    public void onClickReturn(ActionEvent event) throws IOException {
        sideBarController.switchToSite(event);
    }

    public void setHistoricSite(Site site) {
        this.site = site;
        nameText.setText(site.getName());
        locationText.setText(site.getLocation());
        constructionDateText.setText(site.getConstructionDate());
        noteText.setText(site.getNote());
        categoryText.setText(site.getCategory());
        approvedText.setText(site.getApproved());
        
        for (Map.Entry<String, Integer> entry : site.getRelatedFigure().entrySet()){
        	
            Text figureText = new Text(entry.getKey());
            if(entry.getValue() != null) {
                figureText.setFill(Color.web("#3498db"));
                figureText.setUnderline(true);
                figureText.setOnMouseClicked(mouseEvent -> {
                    HistoricalFigure figure = HistoricalFigures.collection.get(entry.getValue());
                    try {
                        FXMLLoader loader = new FXMLLoader(URLCreator.createURLFromPath("/application/view/FigDetail.fxml"));
                        Parent root = loader.load();
                        FigureDetailController controller = loader.getController();
                        controller.setFigure(figure);
                        Scene scene = new Scene(root);
                        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                });
                relatedCharsFlowPane.getChildren().add(figureText);
            }

        }
    }
}

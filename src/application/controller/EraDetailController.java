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
import model.Era;
import model.HistoricalFigure;

public class EraDetailController {
	@FXML
    private FlowPane kingsFlowPane;

    @FXML
    private Text nameText;

    @FXML
    private Text timeStampText;

    @FXML
    private Text homelandText;

    @FXML
    private Text founderText;

    @FXML
    private Text capitalText;

    @FXML
    private Text timeText;

    @FXML
    private Text overviewText;

    @FXML
    private SideBarController sideBarController;

    private Era era;

    @FXML
    public void onClickReturn(ActionEvent event) throws IOException {
        sideBarController.switchToEra(event);
    }

    public void setEra(Era era) {
        this.era = era;
        
      
        	nameText.setText(this.era.getName());
            timeStampText.setText(this.era.getBelongsToTimestamp());
            homelandText.setText(this.era.getHomeland());
            founderText.setText(this.era.getFounder());
            capitalText.setText(this.era.getLocationOfCapital());
            timeText.setText(this.era.getTime());
            overviewText.setText(this.era.getOverview());
            
            for(Map.Entry<String, Integer> entry : era.getListOfKings().entrySet()){
                Text kingText = new Text(entry.getKey());
                if(entry.getValue() != null) {
                    kingText.setFill(Color.web("#3498db"));
                    kingText.setUnderline(true);
                    kingText.setOnMouseClicked(mouseEvent -> {
                        HistoricalFigure figure = HistoricalFigures.collection.get(entry.getValue());
                        
              
                            FXMLLoader loader = new FXMLLoader(URLCreator.createURLFromPath("/application/view/FigDetail.fxml"));
                            Parent root;
							
									try {
										root = loader.load();
										FigureDetailController controller = loader.getController();
			                            controller.setFigure(figure);
			                            Scene scene = new Scene(root);
			                            Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
			                            
			                            stage.setScene(scene);
			                            stage.setFullScreen(true);
			                            stage.show();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
								
								
						
                    });
                }
                kingsFlowPane.getChildren().add(kingText);
            }
        
    }
}

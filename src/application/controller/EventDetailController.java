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
import model.Event;
import model.HistoricalFigure;

public class EventDetailController {
	@FXML
    private Text nameText;

    @FXML
    private Text dateText;

    @FXML
    private Text locationText;

    @FXML
    private Text causeText;

    @FXML
    private Text resultText;

    @FXML
    private FlowPane relatedCharsFlowPane;

    @FXML
    private SideBarController sideBarController;

    private Event event;

    @FXML
    public void onClickReturn(ActionEvent event) throws IOException {
        sideBarController.switchToEvent(event);
    }

    public void setEvent(Event event) {
        this.event = event;
        nameText.setText(event.getName());
        dateText.setText(event.getDate());
        locationText.setText(event.getLocation());
        causeText.setText(event.getCause());
        resultText.setText(event.getResult());
        for(Map.Entry<String, Integer> entry : event.getRelatedFigure().entrySet()){
            Text figureText = new Text(entry.getKey());
            if(entry.getValue() != null){
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
                        stage.setResizable(true);
                        stage.show();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                });
            }
            relatedCharsFlowPane.getChildren().add(figureText);
        }
    }
}

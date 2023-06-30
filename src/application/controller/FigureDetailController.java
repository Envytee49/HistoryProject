package application.controller;
import java.io.IOException;

import application.app.URLCreator;
import application.controller.SideBarController;
import database.Eras;
import database.HistoricalFigures;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Era;
import model.HistoricalFigure;

public class FigureDetailController {

    @FXML
    private Text realNameText;

    @FXML
    private Text bornText;

    @FXML
    private Text diedText;

    @FXML
    private Text overviewText;

    @FXML
    private Text reignTimeText;

    @FXML
    private Text eraText;

    @FXML
    private Text fatherText;

    @FXML
    private Text motherText;

    @FXML
    private Text precederText;

    @FXML
    private Text successorText;
    
    private SideBarController sideBarController;

    private HistoricalFigure figure;

    @FXML
    public void onClickReturn(ActionEvent event){
        sideBarController.switchToFig(event);
    }

    public void setFigure(HistoricalFigure figure) {
        this.figure = figure;
        realNameText.setText(figure.getName());
        
        bornText.setText(figure.getBorn());
        diedText.setText(figure.getDied());
        reignTimeText.setText(figure.getWorkTime());
        eraText.setText(figure.getEraKey());
        fatherText.setText(figure.getFatherKey());
        motherText.setText(figure.getMotherKey());
        precederText.setText(figure.getPrecededByKey());
        successorText.setText(figure.getSucceededByKey());

        Era era = Eras.collection.get(figure.getEraValue(figure.getEraKey()));
        HistoricalFigure father = HistoricalFigures.collection.get(figure.getFatherValue(figure.getFatherKey()));
        HistoricalFigure mother = HistoricalFigures.collection.get(figure.getMotherValue(figure.getMotherKey()));
        HistoricalFigure precededFigure = HistoricalFigures.collection.get(figure.getMotherValue(figure.getMotherKey()));
        HistoricalFigure succeededFigure = HistoricalFigures.collection.get(figure.getSucceededByValue(figure.getSucceededByKey()));

        if(era==null) {
        	System.out.println("Era null");
        }else {
        	System.out.println("Era not null.good to go");
        }

        if(era != null) {
            eraText.setFill(Color.web("#3498db"));
            eraText.setOnMouseClicked(mouseEvent -> {
                try {
                    FXMLLoader loader = new FXMLLoader(URLCreator.createURLFromPath("/application/view/EraDetail.fxml"));
                    Parent root = loader.load();
                    EraDetailController controller = loader.getController();
                    controller.setEra(era);
                    Scene scene = new Scene(root);
                    Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e){
                    e.printStackTrace();
                }
            });
        } else {
            eraText.setFill(Color.web("#000000"));
        }
        if(father != null) {
            fatherText.setFill(Color.web("#3498db"));
            fatherText.setOnMouseClicked(mouseEvent -> setFigure(father));
        } else {
            fatherText.setFill(Color.web("#000000"));
        }
        if(mother != null) {
            motherText.setFill(Color.web("#3498db"));
            motherText.setOnMouseClicked(mouseEvent -> setFigure(mother));
        } else {
            motherText.setFill(Color.web("#000000"));
        }
        if(precededFigure != null) {
            precederText.setFill(Color.web("#3498db"));
            precederText.setOnMouseClicked(mouseEvent -> setFigure(precededFigure));
        } else {
            precederText.setFill(Color.web("#000000"));
        }
        if(succeededFigure != null) {
            successorText.setFill(Color.web("#3498db"));
            successorText.setOnMouseClicked(mouseEvent -> setFigure(succeededFigure));
        } else {
            successorText.setFill(Color.web("#000000"));
        }
    }
}

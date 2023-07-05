package application.controller;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import java.util.Map.Entry;

import application.app.URLCreator;
import database.Eras;
import database.HistoricalFigures;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Era;
import model.HistoricalFigure;

public class FigureDetailController {

    @FXML
    private Text nameText;

    @FXML
    private Text bornText;

    @FXML
    private Text diedText;

    @FXML
    private Text workTimeText;

    @FXML
    private Text eraText;

    @FXML
    private Text fatherText;

    @FXML
    private Text motherText;

    @FXML
    private Text precededByText;

    @FXML
    private Text succeededByText;
    
    @FXML
    private SideBarController sideBarController;

    private HistoricalFigure figure;

    @FXML
    public void onClickReturn(ActionEvent event){
        sideBarController.switchToFig(event);
    }

    public void setFigure(HistoricalFigure figure) {
    	try {
    		this.figure = figure;
            
            Set<Entry<String, Integer>> father = this.figure.getFather().entrySet();
        	Entry<String, Integer> fatherName = father.iterator().next();
        	Integer fid = fatherName.getValue();
        	
        	Set<Entry<String, Integer>> era = this.figure.getEra().entrySet();
        	Entry<String, Integer> eraName = era.iterator().next();
        	Integer eid = eraName.getValue();
        	
        	Set<Entry<String, Integer>> mother = this.figure.getMother().entrySet();
        	Entry<String, Integer> motherName = mother.iterator().next();
        	Integer mid = motherName.getValue();
        	
        	Set<Entry<String, Integer>> preceded = this.figure.getPrecededBy().entrySet();
        	Entry<String, Integer> precededName = preceded.iterator().next();
        	Integer pid = precededName.getValue();
        	
        	Set<Entry<String, Integer>> succeeded = this.figure.getSucceededBy().entrySet();
        	Entry<String, Integer> succeededName = succeeded.iterator().next();
        	Integer sid = succeededName.getValue();
            
        	
            nameText.setText(this.figure.getName());
            
            bornText.setText(this.figure.getBorn());
            diedText.setText(this.figure.getDied());
            workTimeText.setText(this.figure.getWorkTime());
            
          
            eraText.setText(eraName.getKey());
            fatherText.setText(fatherName.getKey());
            motherText.setText(motherName.getKey());
            precededByText.setText(precededName.getKey());
            succeededByText.setText(succeededName.getKey());
            
            
            
        	
            Era eraObj = Eras.collection.get(eid);
           
            HistoricalFigure fatherObj = HistoricalFigures.collection.get(fid);
            HistoricalFigure motherObj = HistoricalFigures.collection.get(mid);
            HistoricalFigure precededObj = HistoricalFigures.collection.get(pid);
            HistoricalFigure succeededObj = HistoricalFigures.collection.get(sid);
            
            System.out.println("Era OBJ" + eraObj);
            /*
            if(eraObj != null && (!eraName.getKey().trim().equalsIgnoreCase("Chưa rõ"))) {
                eraText.setFill(Color.web("#3498db"));
                eraText.setUnderline(true);
                eraText.setOnMouseClicked(mouseEvent -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(URLCreator.createURLFromPath("/application/view/EraDetail.fxml"));
                        Parent root = loader.load();
                        EraDetailController controller = loader.getController();
                        controller.setEra(eraObj);
                        Scene scene = new Scene(root);
                        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                });
            }else {
            	
            	eraText.setText("Chưa rõ");
            }*/
            
            if(eraObj == null) {
            	eraText.setText(eraName.getKey());
            	
            }else if(eraObj != null){
                System.out.println("Era OBJ" + eraObj);

            	System.out.println("Figure in there");
            	if(eraObj.getName().trim().equalsIgnoreCase("Chưa rõ")) {
            		eraText.setText("Chưa rõ");
            	}else{
            		eraText.setFill(Color.web("#3498db"));
                    eraText.setUnderline(true);
                    eraText.setOnMouseClicked(mouseEvent -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(URLCreator.createURLFromPath("/application/view/EraDetail.fxml"));
                            Parent root = loader.load();
                            EraDetailController controller = loader.getController();
                            controller.setEra(eraObj);
                            Scene scene = new Scene(root);
                            Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
                            stage.setScene(scene);
                            stage.show();
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    });
            	}
            }
            if(fatherObj != null && (!fatherObj.getName().trim().equalsIgnoreCase("Chưa rõ"))) {
                fatherText.setFill(Color.web("#3498db"));
                fatherText.setUnderline(true);
                fatherText.setOnMouseClicked(mouseEvent -> setFigure(fatherObj));
            }else {
            	fatherText.setText("");
            	
            }
            if(motherObj != null && (!motherObj.getName().trim().equalsIgnoreCase("Chưa rõ"))) {
                motherText.setFill(Color.web("#3498db"));
                motherText.setUnderline(true);
                motherText.setOnMouseClicked(mouseEvent -> setFigure(motherObj));
            }else {
            	motherText.setText("");
            }
            if(precededObj != null && (!precededObj.getName().trim().equalsIgnoreCase("Chưa rõ"))) {
                precededByText.setFill(Color.web("#3498db"));
                precededByText.setUnderline(true);
                precededByText.setOnMouseClicked(mouseEvent -> setFigure(precededObj));
            }else {
            	precededByText.setText("");
            }
            if(succeededObj != null && (!succeededObj.getName().trim().equalsIgnoreCase("Chưa rõ"))) {
                succeededByText.setFill(Color.web("#3498db"));
                succeededByText.setUnderline(true);
                succeededByText.setOnMouseClicked(mouseEvent -> setFigure(succeededObj));
            }else {
            	succeededByText.setText("");
            }
    	}catch(NullPointerException e) {
    		e.printStackTrace();

    	}

    	

    	
        
    }
}

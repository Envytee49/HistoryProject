package application.controller;

import java.io.IOException;

import application.app.URLCreator;
import database.Eras;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Era;

public class EraScreenController {
	@FXML
    private TableView<Era> eraTable;

    @FXML
    private TableColumn<Era,Integer> colEraId;

    @FXML
    private TableColumn<Era,String> colEraName;

    @FXML
    private TableColumn<Era,String> colEraDate;

    @FXML
    private TableColumn<Era,String> colEraTimeStamp;

    @FXML
    private SearchBarController searchBarController;
    
    @FXML
    public void initialize() {
    	
    	//SetCellValueFactory
    	colEraId.setCellValueFactory(new PropertyValueFactory<Era,Integer>("id"));
    	colEraName.setCellValueFactory(new PropertyValueFactory<Era,String>("name"));
    	colEraDate.setCellValueFactory(new PropertyValueFactory<Era,String>("time"));
    	colEraTimeStamp.setCellValueFactory(new PropertyValueFactory<Era,String>("belongsToTimestamp"));
    	
    	//Change the Era ArrayList -> ObservableList and add it to TableView
    	eraTable.setItems(ObservableHelper.getObservableList(Eras.collection.getEntityData()));
    	
    	
    	
    	eraTable.setRowFactory(tableView -> {
            TableRow<Era> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Era era = row.getItem();
               
                    try {
               
                        FXMLLoader loader = new FXMLLoader(URLCreator.createURLFromPath("/application/view/EraDetail.fxml"));
                        Parent root = loader.load();
                        EraDetailController controller = loader.getController();
                        
                        controller.setEra(era);
                        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    }
}

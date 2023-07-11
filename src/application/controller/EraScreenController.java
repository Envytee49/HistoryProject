package application.controller;

import java.io.IOException;
import java.util.function.Predicate;

import database.Eras;
import database.HistoricalFigures;
import helper.ObservableHelper;
import helper.URLCreator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import model.HistoricalFigure;

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
                        stage.setResizable(true);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    	
    	// set searchBoxListener
    	this.searchBarController.setSearchBoxListener(
    			new SearchBoxListener() {

			@Override
			public void handleSearchName(String name) {
				// Change the type of observable/filtered list to HistoricalFigure/Era/... 
				ObservableList<Era> observingList = ObservableHelper.getObservableList(Eras.collection.getEntityData());
				Predicate<? super Era> idFilterPred = 
						i -> i.isMatch(name);
				FilteredList<Era> filteredFig = 
						new FilteredList<>(observingList, idFilterPred);
				eraTable.setItems(filteredFig);
			}

			@Override
			public void handleSearchID(int id) {
				// TODO Auto-generated method stub
				ObservableList<Era> observingList = ObservableHelper.getObservableList(Eras.collection.getEntityData());
				Predicate<? super Era> idFilterPred = 
						i -> i.isMatch(id);
				FilteredList<Era> filteredFig = 
						new FilteredList<>(observingList, idFilterPred);
				eraTable.setItems(filteredFig);
			}

			@Override
			public void handleBlank() {
				// TODO Auto-generated method stub
				eraTable.setItems(ObservableHelper.getObservableList(Eras.collection.getEntityData()));
			}
    		
    	});

    }
}

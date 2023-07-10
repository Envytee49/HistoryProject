package application.controller;

import java.io.IOException;
import java.util.function.Predicate;

import database.Eras;
import database.Festivals;
import helper.ObservableHelper;
import helper.URLCreator;
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
import model.Festival;

public class FestivalScreenController {
	
	@FXML
    private TableView<Festival> festivalTable;

    @FXML
    private TableColumn<Festival,Integer> colFesId;

    @FXML
    private TableColumn<Festival,String> colFesName;

    @FXML
    private TableColumn<Festival,String> colFesDate;

    @FXML
    private TableColumn<Festival,String> colFesLocate;

    @FXML
    private SearchBarController searchBarController;
    
    @FXML
    public void initialize() {
    	
    	//SetCellValueFactory
    	colFesId.setCellValueFactory(new PropertyValueFactory<Festival,Integer>("id"));
    	colFesName.setCellValueFactory(new PropertyValueFactory<Festival,String>("name"));
    	colFesDate.setCellValueFactory(new PropertyValueFactory<Festival,String>("date"));
    	colFesLocate.setCellValueFactory(new PropertyValueFactory<Festival,String>("location"));
    	
    	//Change the Era ArrayList -> ObservableList and add it to TableView
    	festivalTable.setItems(ObservableHelper.getObservableList(Festivals.collection.getEntityData()));
    	
    	festivalTable.setRowFactory(tableView -> {
            TableRow<Festival> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (!row.isEmpty())){
                    Festival fes = row.getItem();
                    try {
                        FXMLLoader loader = new FXMLLoader(URLCreator.createURLFromPath("/application/view/FesDetail.fxml"));
                        Parent root = loader.load();
                        FesDetailController controller = loader.getController();
                        controller.setFestival(fes);
                        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setFullScreen(true);
                        stage.show();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    	
    	this.searchBarController.setSearchBoxListener(
    			new SearchBoxListener() {

			@Override
			public void handleSearchName(String name) {
				// Change the type of observable/filtered list to HistoricalFigure/Era/... 
				ObservableList<Festival> observingList = ObservableHelper.getObservableList(Festivals.collection.getEntityData());
				Predicate<? super Festival> idFilterPred = 
						i -> i.isMatch(name);
				FilteredList<Festival> filteredFig = 
						new FilteredList<>(observingList, idFilterPred);
				festivalTable.setItems(filteredFig);
			}

			@Override
			public void handleSearchID(int id) {
				// TODO Auto-generated method stub
				ObservableList<Festival> observingList = ObservableHelper.getObservableList(Festivals.collection.getEntityData());
				Predicate<? super Festival> idFilterPred = 
						i -> i.isMatch(id);
				FilteredList<Festival> filteredFig = 
						new FilteredList<>(observingList, idFilterPred);
				festivalTable.setItems(filteredFig);
			}

			@Override
			public void handleBlank() {
				// TODO Auto-generated method stub
				festivalTable.setItems(ObservableHelper.getObservableList(Festivals.collection.getEntityData()));
			}
    		
    	});
    }
}

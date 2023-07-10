package application.controller;

import java.io.IOException;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import database.HistoricalFigures;
import helper.ObservableHelper;
import helper.URLCreator;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.HistoricalFigure;

public class FigureScreenController {
	@FXML
    private TableView<HistoricalFigure> figTable;

    @FXML
    private TableColumn<HistoricalFigure,Integer> colFigId;

    @FXML
    private TableColumn<HistoricalFigure,String> colFigName;

    @FXML
    private TableColumn<HistoricalFigure,Map<String, Integer>> colFigEra;

    @FXML
    private TableColumn<HistoricalFigure,String> colFigOverview;

    @FXML
    private SearchBarController searchBarController;
    
    @FXML
    public void initialize() {
    	
    	//SetCellValueFactory
    	colFigId.setCellValueFactory(new PropertyValueFactory<HistoricalFigure,Integer>("id"));
    	colFigName.setCellValueFactory(new PropertyValueFactory<HistoricalFigure,String>("name"));
    	
    	
    	colFigEra.setCellFactory(column->{
    		TableCell<HistoricalFigure, Map<String,Integer>> cell = new TableCell<HistoricalFigure, Map<String,Integer>>(){
    			@Override
    			protected void updateItem(Map<String, Integer> eras, boolean empty) {
    				super.updateItem(eras, empty);
    				
    				if(eras == null || empty) {
    					setText("");//setText(null)
    				}else {
    					String eraString = eras.keySet().stream().collect(Collectors.joining(", "));
    					setText(eraString);
    				}
    			}
    		};
    		return cell;
    	});
    	colFigEra.setCellValueFactory(new PropertyValueFactory<>("era"));
    	colFigOverview.setCellValueFactory(new PropertyValueFactory<HistoricalFigure,String>("workTime"));
    	
    	//Change the Era ArrayList -> ObservableList and add it to TableView
    	figTable.setItems(ObservableHelper.getObservableList(HistoricalFigures.collection.getEntityData()));
    	
    	figTable.setRowFactory(tableView -> {
            TableRow<HistoricalFigure> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (!row.isEmpty())){
                    HistoricalFigure figure = row.getItem();
                    try {
                        FXMLLoader loader = new FXMLLoader(URLCreator.createURLFromPath("/application/view/FigDetail.fxml"));
                        Parent root = loader.load();
                        FigureDetailController controller = loader.getController();
                        controller.setFigure(figure);
                        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e){
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
				ObservableList<HistoricalFigure> observingList = ObservableHelper.getObservableList(HistoricalFigures.collection.getEntityData());
				Predicate<? super HistoricalFigure> idFilterPred = 
						i -> i.isMatch(name);
				FilteredList<HistoricalFigure> filteredFig = 
						new FilteredList<>(observingList, idFilterPred);
				figTable.setItems(filteredFig);
			}

			@Override
			public void handleSearchID(int id) {
				// TODO Auto-generated method stub
				ObservableList<HistoricalFigure> observingList = ObservableHelper.getObservableList(HistoricalFigures.collection.getEntityData());
				Predicate<? super HistoricalFigure> idFilterPred = 
						i -> i.isMatch(id);
				FilteredList<HistoricalFigure> filteredFig = 
						new FilteredList<>(observingList, idFilterPred);
				figTable.setItems(filteredFig);
			}

			@Override
			public void handleBlank() {
				// TODO Auto-generated method stub
				figTable.setItems(ObservableHelper.getObservableList(HistoricalFigures.collection.getEntityData()));
			}
    		
    	});
    }
}

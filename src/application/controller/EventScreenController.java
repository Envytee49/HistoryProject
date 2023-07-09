package application.controller;


import java.io.IOException;
import java.util.function.Predicate;

import application.helper.ObservableHelper;
import application.helper.URLCreator;
import database.Eras;
import database.Events;
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
import model.Event;

public class EventScreenController {
	
	@FXML
    private TableView<Event> eventTable;

    @FXML
    private TableColumn<Event,Integer> colEventId;

    @FXML
    private TableColumn<Event,String> colEventName;

    @FXML
    private TableColumn<Event,String> colEventDate;

    @FXML
    private TableColumn<Event,String> colEventLocation;

    @FXML
    private SearchBarController searchBarController;
    
    @FXML
    public void initialize() {

        colEventId.setCellValueFactory(
                new PropertyValueFactory<Event, Integer>("id"));
        colEventName.setCellValueFactory(
                new PropertyValueFactory<Event, String>("name"));
        colEventDate.setCellValueFactory(
                new PropertyValueFactory<Event, String>("date"));
        colEventLocation.setCellValueFactory(
                new PropertyValueFactory<Event, String>("location")
        );
        eventTable.setItems(ObservableHelper.getObservableList(Events.collection.getEntityData()));
        
        eventTable.setRowFactory(tableView -> {
            TableRow<Event> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if(mouseEvent.getClickCount() == 2 && (!row.isEmpty())){
                    Event event = row.getItem();
                    try {
                        FXMLLoader loader = new FXMLLoader(URLCreator.createURLFromPath("/application/view/EventDetail.fxml"));
                        Parent root = loader.load();
                        EventDetailController controller = loader.getController();
                        controller.setEvent(event);
                        Scene scene = new Scene(root);
                        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
                        stage.setScene(scene);
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
				ObservableList<Event> observingList = ObservableHelper.getObservableList(Events.collection.getEntityData());
				Predicate<? super Event> idFilterPred = 
						i -> i.isMatch(name);
				FilteredList<Event> filteredFig = 
						new FilteredList<>(observingList, idFilterPred);
				eventTable.setItems(filteredFig);
			}

			@Override
			public void handleSearchID(int id) {
				// TODO Auto-generated method stub
				ObservableList<Event> observingList = ObservableHelper.getObservableList(Events.collection.getEntityData());
				Predicate<? super Event> idFilterPred = 
						i -> i.isMatch(id);
				FilteredList<Event> filteredFig = 
						new FilteredList<>(observingList, idFilterPred);
				eventTable.setItems(filteredFig);
			}

			@Override
			public void handleBlank() {
				// TODO Auto-generated method stub
				eventTable.setItems(ObservableHelper.getObservableList(Events.collection.getEntityData()));
			}
    		
    	});
    }   
}

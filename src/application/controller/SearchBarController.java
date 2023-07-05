package application.controller;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class SearchBarController<T> {
	@FXML
	private TextField searchBox;
	
	@FXML
	private ComboBox<String> filterComboBox;
	
	private SearchBoxListener searchBoxListener;
	
	public void setSearchBoxListener(SearchBoxListener searchBoxListener) {
		this.searchBoxListener = searchBoxListener;
	}
	
	private void showFilteredResults(String newValue) {
		// Empty Search Box
		if(searchBox.getText().equals("")) {
			searchBoxListener.handleBlank();
		}
		else {
			// Filter by Name
			/*Original
			if(filterComboBox.getSelectionModel().getSelectedItem().equals("By Name")) {
				searchBoxListener.handleSearchName(newValue);
			}
			*/
			if(filterComboBox.getValue().equals("By Name")) {
				searchBoxListener.handleSearchName(newValue);
				
			}
			// filter By ID
			
			else if(filterComboBox.getValue().equals("By ID")) {
				try {
					int idFilter = Integer.parseInt(newValue);
					searchBoxListener.handleSearchID(idFilter);
				}
				catch(NumberFormatException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@FXML 
	private void initialize() {
		// Create options for ComboBox
		filterComboBox.setItems(FXCollections.observableArrayList("By Name", "By ID"));
		filterComboBox.getSelectionModel().selectFirst();
		
		// Add ChangeListener for SearchBox, if one modifies the text property of that box, it'll perform actions
		searchBox.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observables, 
				String oldValue, String newValue){
					showFilteredResults(newValue);
			}
		});
	}
}

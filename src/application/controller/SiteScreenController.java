package application.controller;

import java.io.IOException;
import application.app.URLCreator;
import database.Sites;
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
import model.Site;

public class SiteScreenController {
	
	@FXML
    private TableView<Site> siteTable;

    @FXML
    private TableColumn<Site,Integer> colSiteId;

    @FXML
    private TableColumn<Site,String> colSiteName;

    @FXML
    private TableColumn<Site,String> colSiteDate;

    @FXML
    private TableColumn<Site,String> colSiteLocate;

    @FXML
    private SearchBarController searchBarController;
    
    @FXML
    public void initialize() {
    	
    	//SetCellValueFactory
    	colSiteId.setCellValueFactory(new PropertyValueFactory<Site,Integer>("id"));
    	colSiteName.setCellValueFactory(new PropertyValueFactory<Site,String>("name"));
    	colSiteDate.setCellValueFactory(new PropertyValueFactory<Site,String>("constructionDate"));
    	colSiteLocate.setCellValueFactory(new PropertyValueFactory<Site,String>("location"));
    	
    	//Change the Era ArrayList -> ObservableList and add it to TableView
    	siteTable.setItems(ObservableHelper.getObservableList(Sites.collection.getEntityData()));
    	
    	siteTable.setRowFactory(tableView -> {
            TableRow<Site> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (!row.isEmpty())){
                    Site site = row.getItem();
                    try {
                        FXMLLoader loader = new FXMLLoader(URLCreator.createURLFromPath("/application/view/SiteDetail.fxml"));
                        Parent root = loader.load();
                        SiteDetailController controller = loader.getController();
                        controller.setHistoricSite(site);
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
    }
}

package application.controller;

import java.io.IOException;

import application.author.Member;
import application.author.MemberList;
import helper.ImagePath;
import helper.URLCreator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AboutUsController {
	
	private MemberList list;
	
	@FXML
    private SideBarController sideBarController;
	
	@FXML 
	public void onClickReturn(ActionEvent event) throws IOException {
		sideBarController.switchToMain(event);
	}
	
	@FXML
	public void initialize() {
		this.list = new MemberList();
		this.list.addMember(new Member("Nguyễn Viết Thuận","20210826","Captain & Project Manager","Draw class diagrams and package diagrams, implement data crawling, and write report.",ImagePath.getImageAbsolutePath("thuan.jpg")));
		this.list.addMember(new Member("Nguyễn Hà Tâm","20215241","Member","Design the GUI of the program.",ImagePath.getImageAbsolutePath("tam.jpg")));
		this.list.addMember(new Member("Nguyễn Văn Hiếu","20215204","Member","Implement data crawling, write report",ImagePath.getImageAbsolutePath("hieuchon.jpg")));
		this.list.addMember(new Member("Nguyễn Văn Dương","20215189","Member","Implement data crawling + Design powerpoint.",ImagePath.getImageAbsolutePath("duong.jpg")));
		this.list.addMember(new Member("Nguyễn Chấn Hưng","20215209","Member","Implement data crawling + supporter of implement controller of the program.",ImagePath.getImageAbsolutePath("hung.jpg")));
		this.list.addMember(new Member("Thân Hải Phong","20210678","Member","Implement the controller of the program: user interactions.",ImagePath.getImageAbsolutePath("phong.jpg")));
	}
	
	public void switchMember(MouseEvent event, String sid) throws IOException {
		FXMLLoader loader = new FXMLLoader(URLCreator.createURLFromPath("/application/view/AboutUsDetail.fxml"));
        Parent root = loader.load();
        AboutUsDetailController controller = loader.getController();
        
        Member person = list.isMatch(sid);
        controller.setMember(person);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
	}
	
	@FXML
	public void toThuan(MouseEvent event) throws IOException {
		switchMember(event,"20210826");
	}
	
	@FXML
	public void toHung(MouseEvent event) throws IOException {
		switchMember(event,"20215209");
	}
	
	public void toPhong(MouseEvent event) throws IOException {
		switchMember(event,"20210678");
	}
	
	public void toTam(MouseEvent event) throws IOException {
		switchMember(event,"20215241");
	}
	
	public void toDuong(MouseEvent event) throws IOException {
		switchMember(event,"20215189");
        
	}
	
	public void toHieu(MouseEvent event) throws IOException {
		switchMember(event,"20215204");
	}
	
	public MemberList getMemberList() {
		return this.list;
	}
}

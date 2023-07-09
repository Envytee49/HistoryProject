package application.controller;

import java.io.IOException;

import application.author.Member;
import application.author.MemberList;
import application.helper.ImagePath;
import application.helper.URLCreator;
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
		this.list.addMember(new Member("Nguyễn Viết Thuận","00000000","Captain","Crawler,Project Manager",ImagePath.getImageAbsolutePath("thuan.jpg")));
		this.list.addMember(new Member("Nguyễn Hà Tâm","00000001","Captain","Crawler,Project Manager",ImagePath.getImageAbsolutePath("tam.jpg")));
		this.list.addMember(new Member("Nguyễn Văn Hiếu","00000002","Captain","Crawler,Project Manager",ImagePath.getImageAbsolutePath("hieuchon.jpg")));
		this.list.addMember(new Member("Nguyễn Văn Dương","00000003","Captain","Crawler,Project Manager",ImagePath.getImageAbsolutePath("duong.jpg")));
		this.list.addMember(new Member("Nguyễn Chấn Hưng","00000004","Captain","Crawler,Project Manager",ImagePath.getImageAbsolutePath("hung.jpg")));
		this.list.addMember(new Member("Thân Hải Phong","20210678","Captain","Crawler,Project Manager",ImagePath.getImageAbsolutePath("phong.jpg")));
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
		switchMember(event,"00000000");
	}
	
	@FXML
	public void toHung(MouseEvent event) throws IOException {
		switchMember(event,"00000004");
	}
	
	public void toPhong(MouseEvent event) throws IOException {
		switchMember(event,"20210678");
	}
	
	public void toTam(MouseEvent event) throws IOException {
		switchMember(event,"00000001");
	}
	
	public void toDuong(MouseEvent event) throws IOException {
		switchMember(event,"00000003");
        
	}
	
	public void toHieu(MouseEvent event) throws IOException {
		switchMember(event,"00000002");
	}
	
	public MemberList getMemberList() {
		return this.list;
	}
}

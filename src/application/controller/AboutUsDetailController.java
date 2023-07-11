package application.controller;

import application.author.Member;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class AboutUsDetailController {
	
	private Member member;
	
	@FXML
	private Text memNameText;
	
	@FXML
	private Text codeText;
	
	@FXML
	private Text roleText;
	
	@FXML
	private Text contributeText;
	
	@FXML
	private ImageView memImageView;
	
	@FXML
	private SideBarController sideBarController;
	
	@FXML
	public void onClickReturn(ActionEvent event) {
		sideBarController.switchToAboutUs(event);
	}
	
	public void setMember(Member person) {
		this.member = person;
		memNameText.setText(this.member.getName());
		codeText.setText(this.member.getCode());
		roleText.setText(this.member.getRole());
		contributeText.setText(this.member.getContribution());
		
		Image image = new Image(this.member.getImagePath());
		memImageView.setImage(image);
		
	}
}

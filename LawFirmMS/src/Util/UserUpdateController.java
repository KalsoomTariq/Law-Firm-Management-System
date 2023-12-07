package Util;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class UserUpdateController {
	
	@FXML
	private Label nameLabel;
	@FXML
	private Label cnicLabel;
	@FXML
	private Label statusLabel;
	@FXML
	private TextField emailField;
	@FXML
	private TextField contactField;
	@FXML
	private TextField addressField;
	@FXML
	private TextField newPField;
	
	
	private Stage updateStage;
	private User u;
//	private UserLawyer ul;
    public void setDialogStage(Stage s) {
        this.updateStage = s;
        
    	statusLabel.setText("");
    	
    	nameLabel.setText(u.getName());
    	cnicLabel.setText(u.getCNIC());
    	addressField.setText(u.getAddress());
    	contactField.setText(u.getContact());
    	emailField.setText(u.getEmail());
    	newPField.setText(u.getPassword());
    }
	
	@FXML
	private Button saveButton;
	public void saveButtonOnAction(ActionEvent event) {
		statusLabel.setText("");
		u.setAddress(addressField.getText());
		u.setContact(contactField.getText());
		u.setEmail(emailField.getText());
		u.setPassword(newPField.getText());
		
		if(u.updateProfile())
		{
			statusLabel.setText("Changes Saved Successfully");
		}
		else
		{
			statusLabel.setText("Changes not Saved");
			statusLabel.setTextFill(Color.RED);
		}
		
		
	}
	
	
	@FXML
	private void exitForm() {
		statusLabel.setText("");
		updateStage.close();
	}
	
	public void setUa(User u) {
		this.u = u;
	}
	
	

}

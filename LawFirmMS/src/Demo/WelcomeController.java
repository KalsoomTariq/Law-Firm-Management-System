package Demo;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class WelcomeController {

	private Parent root;
	private FXMLLoader loader;
	private Stage stage;

	@FXML
	private Button adminLoginButton;
	public void adminLoginButtonOnAction(ActionEvent e) {
		loader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
		try {
			root = loader.load();
			 // Get the controller and pass the string
	         LoginController lc = loader.getController();
	         lc.setRole("Admins");
	         // Change the scene
	         stage = (Stage)adminLoginButton.getScene().getWindow();
	         stage.setScene(new Scene(root));
	         stage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	@FXML
	private Button lawyerLoginButton;
	public void lawyerLoginButtonOnAction(ActionEvent e) {
		loader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
		try {
			root = loader.load();
			 // Get the controller and pass the string
	         LoginController lc = loader.getController();
	         lc.setRole("Lawyers");
	         // Change the scene
	         stage = (Stage) lawyerLoginButton.getScene().getWindow();
	         stage.setScene(new Scene(root));
	         stage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        
	}
	
}

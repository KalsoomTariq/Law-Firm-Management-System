package Demo;

import java.io.IOException;
import Admin.AdminController;
import Lawyer.LawyerController;
import Util.DialogController;
import Util.Validation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginController {
	
	FXMLLoader loader;
	Parent root;
	Stage dstage;
	Scene scene;
	String role;

	public void setRole(String string) {
		// TODO Auto-generated method stub
		this.role = string;
		System.out.println(role);
	}
	
	@FXML
	private Button loginButton;
	public TextField nameField;
	public PasswordField passwordField;
	public void loginButtonOnAction(ActionEvent e) {
		Validation v = new Validation(role);
		v.setUserName(nameField.getText());
		v.setPassword(passwordField.getText());
		if(v.validateUser()) {
			showDialog("Login Successful");
//			Navigate to the respective main
			
            try {
			
				 if(role.equals("Admins")) {
					String file = "../Admin/"+role+"interface.fxml";
					loader = new FXMLLoader(getClass().getResource(file));
					root = loader.load();
					AdminController ac = loader.getController();
					ac.setUser(nameField.getText());
				 }
				 else
				 {
					 String file = "../Lawyer/"+role+"interface.fxml";
					 loader = new FXMLLoader(getClass().getResource(file));
					 root = loader.load();
					 LawyerController lc = loader.getController();
					 lc.setUser(nameField.getText());
				 }

		         dstage = (Stage)loginButton.getScene().getWindow();
		         dstage.setScene(new Scene(root));
		         dstage.show();
		         
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
		}
		else
		{
			showDialog("Login Unsuccessful");
		}
	}
	
	public void showDialog(String message) {
        try {
        	  // Load the FXML file
            loader = new FXMLLoader(getClass().getResource("../Util/dialogBox.fxml"));
            root = loader.load();

            // Create and set up a new stage
            dstage= new Stage();
            dstage.initModality(Modality.WINDOW_MODAL);
            dstage.setScene(new Scene(root));

            // Set the message in the controller
            DialogController controller = loader.getController();
            controller.setDialogStage(dstage);
            controller.setMessage(message);

            // Show the dialog and wait for the user to close it
            dstage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	@FXML
	private Button backButton;
	public void backButtonOnAction(ActionEvent event) {
		 try {
		        loader = new FXMLLoader(getClass().getResource("welcomePage.fxml"));
		        root = loader.load();

		        dstage = (Stage) backButton.getScene().getWindow();	       
		        scene = new Scene(root);
		        dstage.setScene(scene);
		        dstage.show();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	}

}

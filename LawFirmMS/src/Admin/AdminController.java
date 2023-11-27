package Admin;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Case.User;
import Util.DialogController;
import Util.UserUpdateController;
import Util.jdbConnection;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AdminController implements Initializable {

	FXMLLoader loader;
	Parent root;
	Stage stage;
	User u;
	
	public void setUser(String n) {
		roleLabel.setText("Welcome Admin");
		jdbConnection conn = jdbConnection.getInstance();
		String sql = " select * from users u Inner join Admins a on u.userId = a.userId where u.name = ? ";
		try {
			conn.stmt = conn.connection.prepareStatement(sql);
			conn.stmt.setString(1, n);
			System.out.println("Sql Query: "+conn.stmt);
			try (ResultSet rs = conn.stmt.executeQuery()) {
			        if (rs.next()) {
			           u = new User(rs.getInt("adminId"),rs.getInt("userId"),rs.getString("name"),rs.getString("email"),
			        		   rs.getString("phone"),rs.getString("cnic"),rs.getString("address"),rs.getString("password"),
			        		   rs.getString("role"));
			           
			           //set admin interface
			           loadProfile();
			        }
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	


	@FXML
	private Button logout;
	public void logoutOnClick(ActionEvent e) {
		 loader = new FXMLLoader(getClass().getResource("../Demo/welcomePage.fxml"));
         try {
			 root = loader.load();
			 stage = (Stage)logout.getScene().getWindow();
	         stage.setScene(new Scene(root));
	         stage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		}
	}
	
	@FXML
	private Button update;
	public void updateOnClick(ActionEvent event) {
        try {
        	  // Load the FXML file
            loader = new FXMLLoader(getClass().getResource("../Util/userUpdate.fxml"));
            root = loader.load();

            // Create and set up a new stage
            stage= new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(new Scene(root));

            UserUpdateController uc = loader.getController();
            uc.setUa(u);
            uc.setDialogStage(stage);
            
            // Show the dialog and wait for the user to close it
            stage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
        } 
		loadProfile();
	}
	
	@FXML
	private BorderPane contentPage;
	@FXML
	private AnchorPane profile;
	
	@FXML 
	private void dashboardPage(MouseEvent e) {
		System.out.println("On Action dashboard ");
		contentPage.setCenter(profile);
	}
	
	@FXML 
	private void assignCasePage(MouseEvent e) {
		System.out.println("On Action assign ");
		loadPage("assignCasePage");
	}
	@FXML
	private void registerCasePage(MouseEvent e) {
		System.out.println("On Action register ");
		loadPage("registerCasePage");
	}
	
	
	private void loadPage(String page) {
		System.out.println("In Load page: "+page);
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource(page+".fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		contentPage.setCenter(root);
		
	}
	@FXML
	private Label userName;
	@FXML
	private Label contact;
	@FXML
	private Label cnic;
	@FXML
	private Label role;
	@FXML
	private Label address;
	@FXML
	private Label email;
	@FXML
	private Label totalCases;
	@FXML
	private Label casesWon;
	@FXML
	private Label ongoingCases;
	@FXML
	private Label roleLabel;
	
	private void loadProfile() {
		userName.setText(u.getName());
		contact.setText(u.getContact());
		cnic.setText(u.getCNIC());
		role.setText(u.getRole());
		address.setText(u.getAddress());
		email.setText(u.getEmail());
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
}

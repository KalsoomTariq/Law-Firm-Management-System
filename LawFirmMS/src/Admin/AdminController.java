package Admin;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import Util.User;
import Util.UserUpdateController;
import Util.jdbConnection;
import Util.loadData;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdminController implements loadData{
	
	FXMLLoader loader;
	Parent root;
	Stage stage;
	User u;
	
	@Override
	public void setUser(String n) {
		roleLabel.setText("Welcome Admin");
		jdbConnection conn = jdbConnection.getInstance();
		String sql = " select * from users where name = ? OR email = ? ";
		try {
			conn.stmt = conn.connection.prepareStatement(sql);
			conn.stmt.setString(1, n);
			conn.stmt.setString(2, n);
			System.out.println("Sql Query: "+conn.stmt);
			try (ResultSet rs = conn.stmt.executeQuery()) {
			        if (rs.next()) {
			           u = User.getInstance(rs.getInt("userId"),rs.getString("name"),rs.getString("email"),
			        		   rs.getString("phone"),rs.getString("cnic"),rs.getString("address"),rs.getString("password"),
			        		   rs.getString("role"));
			           u.setAddress(rs.getString("address"));
			           u.setCNIC(rs.getString("CNIC"));
			           u.setName(rs.getString("name"));
			           u.setUserId(rs.getInt("userId"));
			           u.setContact(rs.getString("phone"));
			           u.setEmail(rs.getString("email"));
			           u.setRole(rs.getString("role"));
			           u.setPassword(rs.getString("password"));
			           //set admin interface
			           loadProfile();
			           loadStats();
			        }
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	


	@FXML
	private Button logout;
	@Override
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
	@Override
	public void updateOnClick(ActionEvent event) {
        try {
            loader = new FXMLLoader(getClass().getResource("../Util/userUpdate.fxml"));
            root = loader.load();

            stage= new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(new Scene(root));

            UserUpdateController uc = loader.getController();
            uc.setUa(u);
            uc.setDialogStage(stage);
            
            stage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
        } 
		loadProfile();
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
	private Label roleLabel;
	@FXML
	private PieChart pieChart;
	
	public void loadChart() {

	}

	
	private void loadProfile() {
		
		pieChart.getData().clear();
		
		userName.setText(u.getName());
		contact.setText(u.getContact());
		cnic.setText(u.getCNIC());
		role.setText("Admin");
		address.setText(u.getAddress());
		email.setText(u.getEmail());
		
	}
	
	public void loadStats() {
		// populate case details
		String sql = "SELECT \r\n"
				+ "    COUNT(*) AS totalCases,\r\n"
				+ "    SUM(CASE WHEN status = 'won' THEN 1 ELSE 0 END) AS casesWon,\r\n"
				+ "    SUM(CASE WHEN status = 'lost' THEN 1 ELSE 0 END) AS casesLost,\r\n"
				+ "    SUM(CASE WHEN status = 'ongoing' THEN 1 ELSE 0 END) AS casesOngoing\r\n"
				+ "FROM \r\n"
				+ "    Cases;";

		jdbConnection conn = jdbConnection.getInstance();
		
		try {
			conn.stmt = conn.connection.prepareStatement(sql);
			System.out.println("Sql Query: "+conn.stmt);
			try (ResultSet rs = conn.stmt.executeQuery()) {
			        if (rs.next()) {
					 ObservableList<PieChart.Data> pieChartData =
				                FXCollections.observableArrayList(
				                		new PieChart.Data("Cases Won",rs.getInt("casesWon") ),
				                        new PieChart.Data("Cases Lost", rs.getInt("casesLost") ),
				                        new PieChart.Data("Cases Ongoing", rs.getInt("casesOngoing")));
					 	pieChartData.forEach(data ->
				                data.nameProperty().bind(
				                        Bindings.concat(
				                                data.getName(), " count: ", data.pieValueProperty()
				                        )
				                )
				        );

				        pieChart.getData().addAll(pieChartData);
			        }
			}

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Page Navigation
	
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
	
	@Override
	public void loadPage(String page) {
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

	
}

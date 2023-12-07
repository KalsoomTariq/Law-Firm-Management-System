package Lawyer;

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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LawyerController implements loadData {
	FXMLLoader loader;
	Parent root;
	Stage stage;
	User u;
	
	@Override
	public void setUser(String n) {
		roleLabel.setText("Welcome Lawyer");
		jdbConnection conn = jdbConnection.getInstance();
		String sql = " select * from users u Inner join Lawyers l on u.userId = l.userId where u.name = ? ";
		try {
			conn.stmt = conn.connection.prepareStatement(sql);
			conn.stmt.setString(1, n);
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
			           //set lawyer interface
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
	private Label totalCases;
	@FXML
	private Label casesWon;
	@FXML
	private Label ongoingCases;
	@FXML
	private Label roleLabel;
	@FXML
	private PieChart pieChart;
	@FXML
	CategoryAxis xAxis = new CategoryAxis();
	@FXML
	NumberAxis yAxis = new NumberAxis();
	@FXML
	XYChart.Series<String, Number> assignedSeries;
	@FXML
	XYChart.Series<String, Number> resolvedSeries ;
	@FXML
	private BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
	
	private void loadProfile() {
		
		userName.setText(u.getName());
		contact.setText(u.getContact());
		cnic.setText(u.getCNIC());
		role.setText("Lawyer");
		address.setText(u.getAddress());
		email.setText(u.getEmail());

	}
	
	public void loadStats(){
		xAxis.setLabel("Lawyer ID");
    	yAxis.setLabel("Number of Hearings");
    	barChart.setTitle("Lawyer Performance");
    	
    	
    	assignedSeries = new XYChart.Series<>();
    	assignedSeries.setName("Total Assigned");

    	resolvedSeries = new XYChart.Series<>();
    	resolvedSeries.setName("Total Resolved");
		
		// populate lawyer details
		String sql = "SELECT \r\n"
				+ "    l.lawyerId,\r\n"
				+ "    u.userId AS uId,\r\n"
				+ "    u.name AS LawyerName,\r\n"
				+ "    COUNT(*) AS TotalAssigned,\r\n"
				+ "    SUM(CASE WHEN hd.status = 'Resolved' THEN 1 ELSE 0 END) AS TotalResolved\r\n"
				+ "FROM \r\n"
				+ "    Lawyers l\r\n"
				+ "JOIN \r\n"
				+ "    HearingDates hd ON l.lawyerId = hd.lawyerId\r\n"
				+ "JOIN \r\n"
				+ "    Users u ON l.userId = u.userId\r\n"
				+ "WHERE \r\n"
				+ "    hd.status IN ('Assigned', 'Resolved')\r\n"
				+ "GROUP BY \r\n"
				+ "    l.lawyerId, u.name;";
		int a=0;
		int r=0;
		int t=0;

		jdbConnection conn = jdbConnection.getInstance();
		try {
			conn.stmt = conn.connection.prepareStatement(sql);
			System.out.println("Sql Query: "+conn.stmt);
			try (ResultSet rs = conn.stmt.executeQuery()) {
			        while (rs.next()) {
			        	if (rs.getInt("uId") == u.getUserId()) {
			        		a=rs.getInt("TotalAssigned");
			        		r = rs.getInt("TotalResolved");
			        	}
			        	String name = rs.getString("LawyerName");
			            int lawyerId = rs.getInt("lawyerId");
			            int totalAssigned = rs.getInt("TotalAssigned");
			            int totalResolved = rs.getInt("TotalResolved");
			            t+=totalAssigned;
			            t+=totalResolved;
			            System.out.println(lawyerId + "  "+totalAssigned + " "+ totalResolved);
			            int index = name.indexOf(" ");
			        	assignedSeries.getData().add(new XYChart.Data<>(name.substring(0,index), totalAssigned));
			        	resolvedSeries.getData().add(new XYChart.Data<>(name.substring(0,index), totalResolved));
			}
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	barChart.getData().addAll(assignedSeries, resolvedSeries);
			
		 ObservableList<PieChart.Data> pieChartData =
	                FXCollections.observableArrayList(
	                		new PieChart.Data("Total Dates",t ),
	                        new PieChart.Data("Assigned", a),
	                        new PieChart.Data("Resolved", r));
		 
		 	pieChartData.forEach(data ->
	                data.nameProperty().bind(
	                        Bindings.concat(
	                                data.getName(), " count: ", data.pieValueProperty()
	                        )
	                )
	        );

	        pieChart.getData().addAll(pieChartData);
	        
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
	private void updateCasePage(MouseEvent e) {
		System.out.println("On Action Update Case ");
		loadPage("updateCasePage");
	}
	@FXML
	private void viewSchedulePage(MouseEvent e) {
		System.out.println("On Action View Schedule ");
		loadPage("viewSchedulePage");
	}
	
	@Override
	public void loadPage(String page) {
		System.out.println("In Load page: "+page);
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource(page+".fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		contentPage.setCenter(root);
		
	}
	
}

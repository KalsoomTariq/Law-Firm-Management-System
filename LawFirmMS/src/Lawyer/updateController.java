package Lawyer;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Case.CaseData;
import Case.User;
import Case.caseDateController;
import Util.caseDetailsController;
import Util.jdbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class updateController implements Initializable{
	FXMLLoader loader;
	Parent root;
	Stage stage;
	User u;
	
	@FXML
	private TableView<CaseData> updateCase;
	@FXML
	private TableColumn<CaseData,Integer> caseIdColumn;
	@FXML
	private TableColumn<CaseData,String> clientNameColumn;
	@FXML
	private TableColumn<CaseData,String> updateCaseColumn;
	@FXML
	private TableColumn<CaseData,String> caseTypeColumn;
	@FXML
	private TableColumn<CaseData, String> caseDetailsColumn;
	@FXML
	private ObservableList<CaseData> caseObsList =FXCollections.observableArrayList();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		u = User.getInstance(0, null, null, null, null, null, null, null);
		jdbConnection conn = jdbConnection.getInstance();
		String sql = "SELECT c.caseId, u.name AS clientName, c.caseType, c.details FROM Cases c JOIN Users u ON c.clientId = u.userId JOIN \r\n"
				+ "HearingDates hd ON c.caseId = hd.caseId JOIN "
				+ "Lawyers l ON hd.lawyerId = l.lawyerId "
				+ "WHERE l.userId = ? AND hd.status = 'Assigned';";
		try {
			conn.stmt = conn.connection.prepareStatement(sql);
			conn.stmt.setInt(1, u.getUserId() );
			System.out.println("Sql Query: "+conn.stmt);
			try (ResultSet rs = conn.stmt.executeQuery()) {
				while(rs.next()) {
					Integer cid = rs.getInt("caseId");
					String cname = rs.getString("clientName");
					String st = rs.getString("details");
					String ctp = rs.getString("caseType");
					
					caseObsList.add(new CaseData(cid,cname,st,ctp));
				
				}
				
				caseIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
				clientNameColumn.setCellValueFactory(new PropertyValueFactory<>("clientName"));
				caseTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
				
				// insert view case button in each row
				Callback<TableColumn<CaseData,String>,TableCell<CaseData,String>> cellFactory1 = (param)->{
					
					//Make tablecell with buttons
					final TableCell<CaseData,String> cell = new TableCell<>() {
						//override
						@Override
						public void updateItem(String item,boolean empty) {
							super.updateItem(item, empty);
							if(empty) {
								setGraphic(null);
								setText(null);
							}else
							{
								// create button
								final Button editButton = new Button("View Details");
								editButton.setOnAction(event->{
									CaseData c = getTableView().getItems().get(getIndex());
									System.out.println("Case Id: "+c.getId());
									 try {
								        	  // Load the FXML file
											loader = new FXMLLoader(getClass().getResource("../Util/caseDetails.fxml"));
								            root = loader.load();

								            // Create and set up a new stage
								            stage= new Stage();
								            stage.initModality(Modality.WINDOW_MODAL);
								           
								            stage.setScene(new Scene(root));
								            caseDetailsController cd = loader.getController();
								            cd.setCase(c);
								            cd.setDialogStage(stage);
								            
								            
								            
								            // Show the dialog and wait for the user to close it
								            stage.showAndWait();
								            
								        } catch (IOException e) {
								            e.printStackTrace();
								        } 
										
									
								});
								
								editButton.setStyle("-fx-background-color: #9E7E41; -fx-text-fill: #ffebc6; -fx-font-size: 12px;");

								setGraphic(editButton);
								setText(null);
							}
							
							
							
						};
					};
					
					
					
					
					return cell;
				};
				
				// insert view case button in each row
				Callback<TableColumn<CaseData,String>,TableCell<CaseData,String>> cellFactory2 = (param)->{
					
					//Make tablecell with buttons
					final TableCell<CaseData,String> cell = new TableCell<>() {
						//override
						@Override
						public void updateItem(String item,boolean empty) {
							super.updateItem(item, empty);
							if(empty) {
								setGraphic(null);
								setText(null);
							}else
							{
								// create button
								final Button editButton = new Button("Update");
								editButton.setOnAction(event->{
									CaseData c = getTableView().getItems().get(getIndex());
									System.out.println("Case Id: "+c.getId());
									 try {
								        	  // Load the FXML file
											loader = new FXMLLoader(getClass().getResource("../Case/updateCase.fxml"));
								            root = loader.load();

								            // Create and set up a new stage
								            stage= new Stage();
								            stage.initModality(Modality.WINDOW_MODAL);
								           
								            stage.setScene(new Scene(root));
								            caseDateController cd = loader.getController();
								            cd.setCase(c);
								            cd.setDialogStage(stage);
								            
								            
								            
								            // Show the dialog and wait for the user to close it
								            stage.showAndWait();
								            
								        } catch (IOException e) {
								            e.printStackTrace();
								        } 
										
									
								});
								
								editButton.setStyle("-fx-background-color: #9E7E41; -fx-text-fill: #ffebc6; -fx-font-size: 12px;");

								setGraphic(editButton);
								setText(null);
							}
							
							
							
						};
					};
					
					
					
					
					return cell;
				};
		             
				updateCaseColumn.setCellFactory(cellFactory2);
				caseDetailsColumn.setCellFactory(cellFactory1);
				updateCase.setItems(caseObsList);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}

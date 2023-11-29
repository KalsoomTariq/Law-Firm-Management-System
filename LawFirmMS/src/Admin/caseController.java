package Admin;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.Icon;

import Case.CaseData;
import Case.RegistrationController;
import Util.UserUpdateController;
import Util.caseDetailsController;
import Util.jdbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class caseController implements Initializable {
	FXMLLoader loader;
	Parent root;
	Stage stage;
	
	@FXML
	private TextField searchBar;
	@FXML
	private TableView<CaseData> caseData;
	@FXML
	private TableColumn<CaseData,Integer> caseIdColumn;
	@FXML
	private TableColumn<CaseData,String> clientNameColumn;
	@FXML
	private TableColumn<CaseData,String> statusColumn;
	@FXML
	private TableColumn<CaseData,String> caseTypeColumn;
	@FXML
	private TableColumn<CaseData, String> caseDetailsColumn;
	@FXML
	private ObservableList<CaseData> caseObsList =FXCollections.observableArrayList();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		jdbConnection conn = jdbConnection.getInstance();
		String sql = "SELECT c.caseId, u.name AS clientName, c.status, c.caseType FROM Cases c JOIN Users u ON c.clientId = u.userId;";
		try {
			conn.stmt = conn.connection.prepareStatement(sql);
			System.out.println("Sql Query: "+conn.stmt);
			try (ResultSet rs = conn.stmt.executeQuery()) {
				while(rs.next()) {
					Integer cid = rs.getInt("caseId");
					String cname = rs.getString("clientName");
					String st = rs.getString("status");
					String ctp = rs.getString("caseType");
					
					caseObsList.add(new CaseData(cid,cname,st,ctp));
				
				}
				
				caseIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
				clientNameColumn.setCellValueFactory(new PropertyValueFactory<>("clientName"));
				statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
				caseTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
				
				// insert view case button in each row
				Callback<TableColumn<CaseData,String>,TableCell<CaseData,String>> cellFactory = (param)->{
					
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
		                    
				caseDetailsColumn.setCellFactory(cellFactory);
				caseData.setItems(caseObsList);
				
				FilteredList<CaseData> filterCaseData = new FilteredList<>(caseObsList,b->true);
				searchBar.textProperty().addListener((observable,oldvalue,newvalue)->{
					filterCaseData.setPredicate( caseData -> {
						
						// no search value
						if(newvalue.isEmpty() || newvalue.isBlank() || newvalue == null) {
							return true;
						}
						String srch = newvalue.toLowerCase();
						if(caseData.getClientName().toLowerCase().indexOf(srch)>-1)
						{
							return true;
							
						}
						else if(caseData.getId().toString().indexOf(srch)>-1) {
							
							return true;
						}
						else if(caseData.getStatus().toLowerCase().indexOf(srch)>-1) {
							
							return true;
						}
						
						return false;
					});
				});
				
				SortedList<CaseData> sortedCaseData = new SortedList<>(filterCaseData);
				sortedCaseData.comparatorProperty().bind(caseData.comparatorProperty());
				caseDetailsColumn.setCellFactory(cellFactory);
				caseData.setItems(sortedCaseData);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@FXML
	private Button newCaseButton;
	public void newCaseButtonOnAction(ActionEvent event) {
		 try {
       	  // Load the FXML file
           loader = new FXMLLoader(getClass().getResource("../Case/newCase.fxml"));
           root = loader.load();

           // Create and set up a new stage
           stage= new Stage();
           stage.initModality(Modality.WINDOW_MODAL);
           stage.setScene(new Scene(root));
           
           RegistrationController rc = loader.getController();
           rc.setDialogStage(stage);

           // Show the dialog and wait for the user to close it
           stage.showAndWait();
           
       } catch (IOException e) {
           e.printStackTrace();
       } 
	}
	

}

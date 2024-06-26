package Admin;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import Case.HDateData;
import Util.chooseLawyerController;
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

public class dateController implements Initializable {
	FXMLLoader loader;
	Parent root;
	Stage stage;
	@FXML
	private TableView<HDateData> caseData;
	@FXML
	private TableView<HDateData> assignedData;
	@FXML
	private TableColumn<HDateData,Integer> caseId1;
	@FXML
	private TableColumn<HDateData, Integer> caseId2;
	@FXML
	private TableColumn<HDateData,String> hearingDate1;
	@FXML
	private TableColumn<HDateData,String> hearingTime1;
	@FXML
	private TableColumn<HDateData,String> hearingDate2;
	@FXML
	private TableColumn<HDateData,String> hearingTime2;
	@FXML
	private TableColumn<HDateData,String> assignButton;
	@FXML
	private TableColumn<HDateData, String> lawyer;
	@FXML
	private ObservableList<HDateData> caseObsList =FXCollections.observableArrayList();
	@FXML
	private ObservableList<HDateData> dateObsList =FXCollections.observableArrayList();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		loadT1();
		loadT2();
		
	}
	
	private void loadT1() {
		
		jdbConnection conn = jdbConnection.getInstance();
		String sql = "Select c.caseId , hearingdatetime, hearingId, h.status, notes from cases c join hearingdates h on c.caseId = h.caseId where h.status = 'pending';\r\n"
				+ "";
		try {
			conn.stmt = conn.connection.prepareStatement(sql);
			System.out.println("Sql Query: "+conn.stmt);
			try (ResultSet rs = conn.stmt.executeQuery()) {
				while(rs.next()) {
					Integer cid = rs.getInt("caseId");
					Integer hid = rs.getInt("hearingId");
					String ld = rs.getString("hearingdatetime");
					String st = rs.getString("status");
					String n = rs.getString("notes");
					
					caseObsList.add(new HDateData(hid,cid,ld,st,n));
				
				}
				
				caseId1.setCellValueFactory(new PropertyValueFactory<>("caseId"));
				hearingDate1.setCellValueFactory(new PropertyValueFactory<>("date"));
				hearingTime1.setCellValueFactory(new PropertyValueFactory<>("time"));
				
				// insert view case button in each row
				Callback<TableColumn<HDateData,String>,TableCell<HDateData,String>> cellFactory1 = (param)->{
					
					//Make tablecell with buttons
					final TableCell<HDateData,String> cell = new TableCell<>() {
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
								final Button editButton = new Button("Assign");
								editButton.setOnAction(event->{
									HDateData d = getTableView().getItems().get(getIndex());
									System.out.println("In date contrller");
									System.out.println(d);
									System.out.println("Case Id: "+d.getCaseId());
									 try {
								        	  // Load the FXML file
											loader = new FXMLLoader(getClass().getResource("../Util/assignLawyer.fxml"));
								            root = loader.load();

								            // Create and set up a new stage
								            stage= new Stage();
								            stage.initModality(Modality.WINDOW_MODAL);
								           
								            stage.setScene(new Scene(root));
								            chooseLawyerController cl = loader.getController();
								            cl.setDateData(d);
								            cl.setDialogStage(stage);
								            // Show the dialog and wait for the user to close it
								            stage.showAndWait();
								            reload();
								            
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
		                    
				assignButton.setCellFactory(cellFactory1);
				caseData.setItems(caseObsList);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void loadT2() {
		jdbConnection conn = jdbConnection.getInstance();
		String sql = "SELECT \r\n"
				+ "	hd.caseId,\r\n"
				+ "    hd.status,\r\n"
				+ "    hd.hearingId,\r\n"
				+ "	hd.hearingDateTime,\r\n"
				+ "    u.name AS lawyerName\r\n"
				+ "FROM \r\n"
				+ "    HearingDates hd\r\n"
				+ "JOIN \r\n"
				+ "    Lawyers l ON hd.lawyerId = l.lawyerId\r\n"
				+ "JOIN \r\n"
				+ "    Users u ON l.userId = u.userId\r\n"
				+ "WHERE \r\n"
				+ "    hd.status IN ('Assigned', 'Resolved');";
		try {
			conn.stmt = conn.connection.prepareStatement(sql);
			System.out.println("Sql Query: "+conn.stmt);
			try (ResultSet rs = conn.stmt.executeQuery()) {
				while(rs.next()) {
					Integer cid = rs.getInt("caseId");
					Integer hid = rs.getInt("hearingId");
					String ld = rs.getString("hearingdatetime");
					String st = rs.getString("status");
					String n = rs.getString("lawyerName");
					
					dateObsList.add(new HDateData(hid,cid,ld,st,n));
				
				}
				
				caseId2.setCellValueFactory(new PropertyValueFactory<>("caseId"));
				hearingDate2.setCellValueFactory(new PropertyValueFactory<>("date"));
				hearingTime2.setCellValueFactory(new PropertyValueFactory<>("time"));
				lawyer.setCellValueFactory(new PropertyValueFactory<>("notes"));
				
				
				assignedData.setItems(dateObsList);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void reload() {
		dateObsList.clear();
		caseObsList.clear();
		loadT1();
		loadT2();
	}
	
}

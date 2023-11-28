package Admin;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Case.CaseData;
import Util.jdbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class caseController implements Initializable {
	@FXML
	private TextField searchBar;
	@FXML
	private TableView<CaseData> caseData;
	@FXML
	private TableColumn<CaseData,Integer> caseIdColumn;
	@FXML
	private TableColumn<CaseData,Integer> clientNameColumn;
	@FXML
	private TableColumn<CaseData,Integer> statusColumn;
	@FXML
	private TableColumn<CaseData,Integer> caseTypeColumn;
	@FXML
	private ObservableList<CaseData> caseObsList =FXCollections.observableArrayList();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		jdbConnection conn = jdbConnection.getInstance();
		String sql = "SELECT c.caseId, u.name AS clientName, c.status, c.caseType FROM Cases c JOIN Clients cl ON c.clientId = cl.clientId JOIN Users u ON cl.userId = u.userId;";
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
						
						return false;
					});
				});
				
				SortedList<CaseData> sortedCaseData = new SortedList<>(filterCaseData);
				sortedCaseData.comparatorProperty().bind(caseData.comparatorProperty());
				caseData.setItems(sortedCaseData);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@FXML
	private Button newCaseButton;
	public void newCaseButtonOnAction(ActionEvent e) {
		
	}
}

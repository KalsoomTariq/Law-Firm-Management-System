package Case;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import Util.jdbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistrationController implements Initializable {
	
	private Stage updateStage;
	private HashMap<String,Integer> map;
	@FXML
	private DatePicker datePicker;
	@FXML
	private ChoiceBox<String> hourPick;
	@FXML
	private ChoiceBox<String> minPick;
	@FXML
	private TextField clientNameField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField contactField;
	@FXML
	private TextArea detailsField;
	@FXML
	private Button registerCaseButton;
	public void  registerCaseButtonOnAction(ActionEvent event) {
		User u = new User();
		CaseData c = new CaseData();
		jdbConnection conn = jdbConnection.getInstance();
		if(clientNameField.getText().isEmpty() && chooseClientCombo.getValue().isEmpty()) {
			System.out.println("Changes not saved");
			return;
		}else if(!clientNameField.getText().isEmpty()) {
			
			String[] columns = {"name","email", "phone", "role"};
			Object[] obj = {clientNameField.getText(),emailField.getText(),contactField.getText(),"Client"};
			if(conn.insertData("users", columns ,obj )) {
				String n = " name = '"+clientNameField.getText()+"'";
				String[] s= {"userId"};
				ResultSet rs = conn.readData("users", s, n);
				Integer Id=0;
				try {
					if(rs.next()) {
						Id = rs.getInt("userId");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// put data in cases table
				String[] col = {"clientId","caseType", "startDate", "status", "details"};
				Object[] ob = {Id,chooseCase.getValue(),LocalDate.now(),"Ongoing",detailsField.getText()};
				if(conn.insertData("cases", col ,ob )) {
					// Fetch the caseId by unique client
					String[] cl = {"caseId"};
					String con=" clientId = "+Id;
					ResultSet rs1 = conn.readData("cases",cl, con);
					try {
						while(rs1.next()) {
							Integer id = rs1.getInt("caseId");
							// Insert Hearing date
							String[] col1 = {"caseId", "hearingDateTime", "status", "notes"};
							// Compose time and date
							String str = datePicker.getValue()+" "+hourPick.getValue()+":"+minPick.getValue()+":00";
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
							LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

							Object[] ob1= {id,dateTime,"Pending",detailsField.getText()};
							if(conn.insertData("hearingdates", col1, ob1)) {
								System.out.println("Hearing date inserted");
							}
							
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
					
					updateStage.close();
				}else {
					System.out.println("Not Inserted");
				}
				
			}else {
				System.out.println("Not Inserted");
			}
		}
		else if(clientNameField.getText().isEmpty()) {
			u.setUserId(map.get(chooseClientCombo.getValue()));
			c.setType(chooseCase.getValue());
			c.setStartDate(LocalDate.now());
			c.setStatus("Ongoing");
			c.setDetails(detailsField.getText());
			System.out.println("ID: "+u.getUserId());
			
			// Existing Client case update;
			ArrayList<Integer> caseIds=new ArrayList<>();
			// fetch existing caseIds
			String[] col1 = {"caseId"};
			String str1 = "clientId = "+u.getUserId();
			ResultSet rs1 = conn.readData("cases", col1, str1);
			try {
				while(rs1.next()) {
					caseIds.add(rs1.getInt("caseId"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String[] columns = {"clientId","caseType", "startDate", "status", "details"};
			Object[] obj = {u.getUserId(),c.getType(),c.getStartDate(),c.getStatus(),c.getDetails()};
			if(conn.insertData("cases", columns ,obj )) {

				// Fetch the caseId by comparing
				ArrayList<Integer> newIds = new ArrayList<>();
				String[] cl = {"caseId"};
				String con=" clientId = "+u.getUserId();
				ResultSet rs2 = conn.readData("cases",cl, con);
				try {
					while(rs2.next()) {
						newIds.add(rs2.getInt("caseId"));	
					}
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			 	Set<Integer> union = new HashSet<>(caseIds);
				union.addAll(newIds); // Union of set1 and set2
				
				Set<Integer> intersection = new HashSet<>(caseIds);
				intersection.retainAll(newIds); // Intersection of set1 and set2
				
				union.removeAll(intersection); 
				Integer val=0;
				for(Integer i : union) {
					val = i;
				}
				
				// Insert Hearing date
				String[] col2 = {"caseId", "hearingDateTime", "status", "notes"};
				// Compose time and date
				String str = datePicker.getValue()+" "+hourPick.getValue()+":"+minPick.getValue()+":00";
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
				LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

				Object[] ob1= {val,dateTime,"Pending",detailsField.getText()};
				if(conn.insertData("hearingdates", col2, ob1)) {
					System.out.println("Hearing date inserted");
				}
				
				updateStage.close();
			}else {
				System.out.println("Not Inserted");
			}
			
		}
		
	}

    public void setDialogStage(Stage s) {
        this.updateStage = s;
    }

	@FXML
	private ChoiceBox<String> chooseClientCombo;
	@FXML
	private ChoiceBox<String> chooseCase;
	public void BindCombo() {
		User ul = new User();
		map = ul.populateCombo();
		for(String s : map.keySet()) {
			chooseClientCombo.getItems().add(s);
		}
		chooseCase.getItems().add("Civil");
		chooseCase.getItems().add("Criminal");
		chooseCase.getItems().add("Family");
		
		hourPick.getItems().add("08");
		hourPick.getItems().add("09");
		hourPick.getItems().add("10");
		hourPick.getItems().add("11");
		hourPick.getItems().add("12");
		hourPick.getItems().add("01");
		hourPick.getItems().add("02");
		hourPick.getItems().add("03");
		hourPick.getItems().add("04");
		hourPick.getItems().add("05");
		
		minPick.getItems().add("00");
		minPick.getItems().add("15");
		minPick.getItems().add("30");
		minPick.getItems().add("45");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		BindCombo();
	}
	
	@FXML
	private void exitForm() {
		updateStage.close();
	}
	
}

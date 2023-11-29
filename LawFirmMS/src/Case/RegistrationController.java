package Case;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;

import Util.jdbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistrationController implements Initializable {
	
	private Stage updateStage;
	private HashMap<String,Integer> map;
	
	@FXML
	private TextField clientNameField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField contactField;
	@FXML
	private TextField detailsField;
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
			
			String[] columns = {"clientId","caseType", "startDate", "status", "details"};
			Object[] obj = {u.getUserId(),c.getType(),c.getStartDate(),c.getStatus(),c.getDetails()};
			if(conn.insertData("cases", columns ,obj )) {
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

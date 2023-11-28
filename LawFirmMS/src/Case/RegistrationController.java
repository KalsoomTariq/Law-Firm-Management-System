package Case;

import java.net.URL;
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
		}
		else if(clientNameField.getText().isEmpty()) {
			u.setUniqueId(map.get(chooseClientCombo.getValue()));
			System.out.println("ID: "+u.getUniqueId());
			c.setClientID(conn.getRowCount("clients")+1);
			c.setId(conn.getRowCount("Cases")+1);
			c.setStartDate(LocalDate.now());
			c.setType(chooseCase.getValue());
			c.setStatus("Ongoing");
			
			String[] columns = {"caseId","clientId","caseType", "startDate", "status", "details"};
			Object[] obj = {c.getId(),u.getUniqueId(),c.getType(),c.getStartDate(),c.getStatus(),c.getDetails()};
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

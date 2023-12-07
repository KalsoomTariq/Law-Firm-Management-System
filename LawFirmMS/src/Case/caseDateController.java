package Case;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import Util.jdbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class caseDateController implements Initializable {
	private Stage updateStage;
	@FXML
	private TextArea detailsField;
	@FXML
	private DatePicker datePicker;
	@FXML
	private ChoiceBox<String> hourPick;
	@FXML
	private ChoiceBox<String> minPick;
	@FXML
	private Button turnInButton;
	CaseData c;
	public void  turnInButtonOnAction(ActionEvent event) {
		if(datePicker.getValue().equals(null) || minPick.getValue().equals(null) 
				|| hourPick.getValue().isEmpty()) {
			updateStage.close();
		}
		/// implementation left
		// Update the status of current date as resolved
		jdbConnection conn = jdbConnection.getInstance();
		String[] col1 = {"status"};
		Object[] obj1 = {"Resolved",c.getHearingId()};
		String wc1 = " hearingId = ? ;";
		if(conn.updateData("hearingdates", col1, obj1, wc1)) {
			System.out.println("Case Resolved");
		}

		// Insert Hearing date
		String[] col2 = {"caseId", "hearingDateTime", "status", "notes"};
		// Compose time and date
		String str = datePicker.getValue()+" "+hourPick.getValue()+":"+minPick.getValue()+":00";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
		LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

		Object[] ob1= {c.getId(),dateTime,"Pending",detailsField.getText()};
		if(conn.insertData("hearingdates", col2, ob1)) {
			System.out.println("Hearing date inserted");
		}
		
		updateStage.close();
	}

    public void setDialogStage(Stage s) {
        this.updateStage = s;
    }

	@FXML
	private ChoiceBox<String> chooseClientCombo;
	@FXML
	private ChoiceBox<String> chooseCase;
	public void BindCombo() {
		
		
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
		BindCombo();
	}
	public void setCase(CaseData c) {
		this.c = c;
		System.out.println(c);
	}
	@FXML
	private void exitForm() {
		updateStage.close();
	}
}

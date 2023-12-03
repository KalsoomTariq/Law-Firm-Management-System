package Case;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class caseDateController implements Initializable {
	private Stage updateStage;
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
		/// implementation left
		
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
	}
	@FXML
	private void exitForm() {
		updateStage.close();
	}
}

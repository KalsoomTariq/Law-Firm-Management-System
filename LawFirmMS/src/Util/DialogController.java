package Util;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DialogController {
	 	@FXML
	    private Label messageLabel;
	    private Stage dialogStage;
	    public void setDialogStage(Stage dialogStage) {
	        this.dialogStage = dialogStage;
	    }

	    public void setMessage(String message) {
	        messageLabel.setText(message);
	    }

	    @FXML
	    private Button okButton;
	    public void handleOk() {
	        dialogStage.close();
	    }
}

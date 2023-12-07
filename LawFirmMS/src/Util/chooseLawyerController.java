package Util;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import Case.HDateData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class chooseLawyerController implements Initializable {

    private Stage dialogStage;
    private HDateData d;
	private HashMap<String,Integer> map;
	
    
    @FXML
    private ChoiceBox<String> chooseLawyer;
	public void BindCombo() {
		map = d.populateCombo();
		for(String s : map.keySet()) {
			chooseLawyer.getItems().add(s);
		}
		System.out.println(map);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        BindCombo();
    }

    public void setDateData(HDateData d) {
        this.d = d;
    }

    @FXML
    private Button chooseButton;
    public void handleOk() {
    	
		jdbConnection conn = jdbConnection.getInstance();
		if(chooseLawyer.getValue().isEmpty()) {
			System.out.println("Changes not saved");
			dialogStage.close();
			
		}
		else
		{
			String[] columns = {"lawyerId","status"};
			System.out.println( map.get(chooseLawyer.getValue()));
			Object[] obj = { map.get(chooseLawyer.getValue()),"Assigned" , d.getHearingId()};
			String s = "hearingId = ?;";
			if(conn.updateData("hearingdates", columns, obj, s)) {
				System.out.println("Updated Successfully");
			}else {
				System.out.println("Not updated");
			}
				
			
		}
        dialogStage.close();
    }
}

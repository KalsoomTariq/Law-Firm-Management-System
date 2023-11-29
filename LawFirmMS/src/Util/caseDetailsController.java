package Util;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Case.CaseData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class caseDetailsController implements Initializable {
	CaseData c;
	
	@FXML
	private Label cNameLabel;
	@FXML
	private Label cEmailLabel;
	@FXML
	private Label cContactLabel;
	@FXML
	private Label cAddressLabel;
	@FXML
	private Label caseTypeLabel;
	@FXML
	private Label statusLabel;
	@FXML
	private Label detailsLabel;
	@FXML
	private Label dateLabel;
	
	private void loadCase() {
		jdbConnection conn = jdbConnection.getInstance();
		String sql = "Select name,email,address,phone,caseType,startDate,status,details from cases c join users u on c.clientid = u.userid where caseid = ?;";
		
		try {
			conn.stmt = conn.connection.prepareStatement(sql);
			conn.stmt.setInt(1, c.getId());
			System.out.println("Sql Query: "+conn.stmt);
			try (ResultSet rs = conn.stmt.executeQuery()) {
			        while(rs.next()) {
			        	// set result to fields
			        	cNameLabel.setText(rs.getString("name"));
			        	cEmailLabel.setText(rs.getString("email"));
			        	cAddressLabel.setText(rs.getString("address"));
			        	cContactLabel.setText(rs.getString("phone"));
			        	caseTypeLabel.setText(rs.getString("caseType"));
			        	dateLabel.setText(rs.getString("startDate"));
			        	statusLabel.setText(rs.getString("status"));
			        	detailsLabel.setText(rs.getString("details"));
			        }
			}
			catch(Exception e) {
				System.out.println("Exception: "+e);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private Stage updateStage;
    public void setDialogStage(Stage s) {
        this.updateStage = s;
        loadCase();
    }
	@FXML
	private void exitForm() {
		statusLabel.setText("");
		updateStage.close();
	}
	
	public void setCase(CaseData c) {
		this.c = c;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}

package Case;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import Util.jdbConnection;

public class HDateData {
	
	
	 @Override
	public String toString() {
		return "HDateData [hearingId=" + hearingId + ", caseId=" + caseId + ", hearingDateTime=" + hearingDateTime
				+ ", status=" + status + ", notes=" + notes + "]";
	}


	private Integer hearingId;
	 private Integer caseId;
	 private LocalDateTime hearingDateTime;
	 private LocalDate date;
	 private LocalTime time;
	 private String status;
	 private String notes;
	 private String caseType;
	 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
	 public HDateData() {}
	 public HDateData(Integer hid, Integer cid, String ld, String st, String n){
		 this.hearingId = hid;
		 this.caseId = cid;
		 this.hearingDateTime = LocalDateTime.parse(ld, formatter);
		 this.date = this.hearingDateTime.toLocalDate();
		 this.time = this.hearingDateTime.toLocalTime();

		 this.notes=n;
		 this.status = st;
	 }
	 
	    public HashMap<String, Integer> populateCombo(){
	    	HashMap<String, Integer> map = new HashMap<>();
	    	jdbConnection conn = jdbConnection.getInstance();
		    String sql = "SELECT DISTINCT lawyerId as ID, name as Name FROM Lawyers l join users u on l.userid = u.userid WHERE l.lawyerId NOT IN (SELECT h.lawyerId FROM HearingDates h WHERE h.hearingDateTime = ? AND h.lawyerId IS NOT NULL);";  

		    try {
		    	conn.stmt = conn.connection.prepareStatement(sql);
		    	conn.stmt.setString(1, this.hearingDateTime.toString());
		    	try (ResultSet rs = conn.stmt.executeQuery()) 
		    	{
			        while(rs.next()) {
			        	String n = rs.getString("Name");
			        	Integer i = rs.getInt("ID");
			        	map.put(n, i);
			        }
			    }
				catch(Exception e) {
						System.out.println("Exception: "+e);
				}			
		        
		    } catch (SQLException e) {
		        System.out.println(e.getMessage());
		    }
	    	return map;
	    }
	 
	 public Integer getHearingId() {
		return hearingId;
	}
	public void setHearingId(Integer hearingId) {
		this.hearingId = hearingId;
	}
	public Integer getCaseId() {
		return caseId;
	}
	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}
	public LocalDateTime getHearingDateTime() {
		return hearingDateTime;
	}
	public void setHearingDateTime(LocalDateTime hearingDateTime) {
		this.hearingDateTime = hearingDateTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}


	public LocalTime getTime() {
		return time;
	}


	public void setTime(LocalTime time) {
		this.time = time;
	}
	public String getCaseType() {
		return caseType;
	}
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}
	
}

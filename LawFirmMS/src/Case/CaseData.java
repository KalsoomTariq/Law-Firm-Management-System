package Case;

import java.time.LocalDate;

public class CaseData {
	@Override
	public String toString() {
		return "CaseData [id=" + id + ", clientID=" + clientID + ", type=" + type + ", details=" + details + ", status="
				+ status + ", startDate=" + startDate + ", clientName=" + clientName + "]";
	}
	private int id;
    private int clientID;
    private String type;
    private String details;
    private String status;
    private LocalDate startDate;
    private String clientName;
    private int hearingId;
    
//    private static String[] columns = {"caseId", "clientId", "caseType", "details", "status", "startDate"};
    
    public CaseData(int id, int clientID, String type, String details, String status, LocalDate startDate) {

    	this.id = id;
    	this.clientID = clientID;
    	this.type = type;
    	this.details = details;
    	this.status = status;
    	this.startDate = startDate;
    	
    }
    public CaseData(int cid, String cName,String status, String type,int hid) {

    	this.id = cid;
    	this.clientName = cName;
    	this.type = type;
    	this.status = status;	
    	this.setHearingId(hid);
    }
    public CaseData() {
    	
    }
    
//    private ArrayList<CaseData> getCases() {
//        ArrayList<CaseData> caseList = new ArrayList<>();
//        try {
//        	jdbConnection conn = jdbConnection.getInstance();
//			try (ResultSet rs = conn.readData("Case", columns, null);) {
//			       while(rs.next()) {
//			    	    CaseData Case_ = new CaseData(rs.getInt("caseId"), rs.getInt("clientId"), rs.getString("caseType"), rs.getString("details"), rs.getString("status"), rs.getDate("startDate"));
//		                caseList.add(Case_); 
//			       }
//			}
//			catch(Exception e) {
//				System.out.println("Exception: "+e);
//			}
//			
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return caseList;
//    }
    
    

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getClientID() {
		return clientID;
	}

	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public Integer getHearingId() {
		return hearingId;
	}
	public void setHearingId(int hearingId) {
		this.hearingId = hearingId;
	}

}
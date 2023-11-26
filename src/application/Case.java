package application;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

public class Case {    
	private int id;
    private int clientID;
    private String type;
    private String details;
    private String status;
    private Date startDate;
    
    private boolean isRegistered;
    
    private static Database db = Database.getInstance();
	private static String table = "Cases";
	private static String[] columns = {"caseId", "clientId", "caseType", "details", "status", "startDate"};
    
    public Case(int id, int clientID, String type, String details, String status, Date startDate) {
//    	id = db.getRowCount(table) + 1;
    	this.id = id;
    	this.clientID = clientID;
    	this.type = type;
    	this.details = details;
    	this.status = status;
    	this.startDate = startDate;
    	
    	isRegistered = false;
    }
    
    public static ArrayList<Case> getCases() {
        ArrayList<Case> caseList = new ArrayList<>();
        try {
            ResultSet rs = db.readData(table, columns, null);

            while (rs.next()) {
                Case Case_ = new Case(rs.getInt("caseId"), rs.getInt("clientId"), rs.getString("caseType"), rs.getString("details"), rs.getString("status"), rs.getDate("startDate"));
                caseList.add(Case_);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return caseList;
    }
    
    public boolean register() {
    	if (isRegistered) return false;
    	
    	Object[] values = {id, clientID, type, details, status, startDate};
    	
    	if (db.insertData(table, columns, values)) {
    		isRegistered = true;
    		
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public boolean update() {
    	Object[] values = {id, clientID, type, details, status, startDate};
    	
    	if (db.updateData(table, columns, values, "caseId = " + id)) {    		
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public boolean remove() {
    	if (db.deleteData(table, "caseId = " + id)) {
    		isRegistered = false;
    		
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public void print() {
    	System.out.println("---------------Case " + id + "---------------");
    	System.out.println("ID:         " + id);
    	System.out.println("Client ID:  " + clientID);
    	System.out.println("Type:       " + type);
    	System.out.println("Detail:     " + details);
    	System.out.println("Status:     " + status);
    	System.out.println("Start Date: " + startDate);
    }
    
    public boolean isRegistered() {
        return isRegistered;
    }
    
    public int getId() {
        return id;
    }

    public int getClientID() {
        return clientID;
    }

    public String getType() {
        return type;
    }

    public String getDetails() {
        return details;
    }

    public String getStatus() {
        return status;
    }

    public Date getStartDate() {
        return startDate;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}

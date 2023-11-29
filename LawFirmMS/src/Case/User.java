package Case;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import Util.jdbConnection;

public class User{

    private int userId;
    private String name;
    private String contact;
    private String CNIC;
    private String address;
    private String email;
	private String password;
    private String role;
    public User() {
    	
    }
    // Constructor
    public User(int userId,String name, String email, String contact, String cnic, String address, String password, String role) {
        this.userId = userId;
        this.password = password;
        this.role = role;
        this.name=name;
        this.email=email;
        this.CNIC=cnic;
        this.address=address;
        this.contact=contact;
    }
   // database logic
    public Boolean updateProfile() {

    	jdbConnection conn = jdbConnection.getInstance();
	    String sql = "UPDATE Users SET email = ?, phone = ?, address = ?, password = ? WHERE userID = ?";

	    try {
	    	conn.stmt = conn.connection.prepareStatement(sql);
	        // Set the corresponding params
	    	conn.stmt.setString(1, this.email);
	    	conn.stmt.setString(2, this.contact);
	    	conn.stmt.setString(3, this.address);
	    	conn.stmt.setString(4, this.password);
	    	conn.stmt.setInt(5, this.userId);

	        // Update 
	        int updatedRows = conn.stmt.executeUpdate();
	        System.out.println("Updated: "+updatedRows);
	        if (updatedRows > 0) {
	            return true;
	        }
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
    	
	    return false;
    }
    
    public HashMap<String, Integer> populateCombo(){
    	HashMap<String, Integer> map = new HashMap<>();
    	jdbConnection conn = jdbConnection.getInstance();
	    String sql = "Select distinct name as Name, userId as ID From Users u Join Cases c On u.userId = c.clientId; "
	    		+ "";

	    try {
	    	conn.stmt = conn.connection.prepareStatement(sql);
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

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getCNIC() {
		return CNIC;
	}

	public void setCNIC(String cNIC) {
		CNIC = cNIC;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	   
}


package Util;

import java.sql.ResultSet;

public class Validation {
	
	String userType;
	String userName;
	String password;
	
	public Validation(String userType) {
		this.userType = String.valueOf(userType);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public Boolean validateUser() {
		try {		
			// Connect to database
			jdbConnection conn = jdbConnection.getInstance();
			String sql = " select * from users u Inner join "+this.userType+" l on u.userId = l.userId  where u.name = ? AND u.password = ? ";
			conn.stmt = conn.connection.prepareStatement(sql);
			conn.stmt.setString(1, this.userName);
			conn.stmt.setString(2, this.password);
			System.out.println("Sql Query: "+conn.stmt);
			try (ResultSet rs = conn.stmt.executeQuery()) {
			        if (rs.next()) {
			           return true;
			        }
			        else
			        {
			        	return false;
			        }
			}
			catch(Exception e) {
				System.out.println("Exception: "+e);
			}
			
		}catch(Exception e) {
			System.out.println("Exception: "+e);
			
		}
		return false;
	}

}

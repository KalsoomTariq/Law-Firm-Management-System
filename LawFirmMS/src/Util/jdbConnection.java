package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class jdbConnection {
	private static jdbConnection instance;
	public Connection connection;
	public PreparedStatement stmt;

    public static jdbConnection getInstance() {
        if (instance == null) {
            instance = new jdbConnection();
        }
        return instance;
    }
    
	private jdbConnection() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql:///LawFirmMS","root","ChangedYou**");	
		} catch(Exception e){
			System.out.println(e);
		}
	}

}

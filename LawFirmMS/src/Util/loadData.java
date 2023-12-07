package Util;

import javafx.event.ActionEvent;
/*
 * ---------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------- 
 * 									INTERFACE CLASS
 * ---------------------------------------------------------------------------------------
 * ---------------------------------------------------------------------------------------
 */


public interface loadData {
	
	public void setUser(String field);
	public void logoutOnClick(ActionEvent event);
	public void updateOnClick(ActionEvent event);
	public void loadPage(String pagename);
}

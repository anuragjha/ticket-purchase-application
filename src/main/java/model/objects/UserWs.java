/**
 * 
 */
package model.objects;

import java.util.ArrayList;

/**
 * @author anuragjha
 *
 */
public class UserWs {

	int userid;
	String username;
	ArrayList<Event> tickets;  //
	

	/**
	 * 
	 */	
	public UserWs(int userid, String username) {
		this.userid = userid;
		this.username = username;
		tickets = new ArrayList<Event>();
	}
	
	
	/**
	 * @param event
	 */
	public void addEvent(Event event) {
		this.tickets.add(event);
	}

}

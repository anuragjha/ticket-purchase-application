/**
 * 
 */
package model.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author anuragjha
 *
 */
public class User {

	int userid;
	String username;
	ArrayList<Ticket> tickets;  //
	
	
	/**
	 * @param result the tickets to set
	 */
	public void setTickets(ResultSet result) {
		
		try {
			while(result.next()) {
				this.tickets.add(new Ticket(result.getInt(1)));
				
			}
		} catch (SQLException e) {
			System.out.println("Error in getting tickets for the user");
			e.printStackTrace();
		}
	}


	public User(int userid, String username) {
		this.userid = userid;
		this.username = username;
		tickets = new ArrayList<Ticket>();
	}
	
	
	/**
	 * 
	 */
//	public User(ResultSet result) {
//		try {
//			if(result.next()) {
//				this.userid = result.getInt(1);
//				System.out.println("User found : " + this.userid);
//				
//				this.tickets = new ArrayList<Ticket>();
//				this.tickets.add(new Ticket(result.getInt(2)));
//				
//				while(result.next()) {
//					this.tickets.add(new Ticket(result.getInt(2)));
//					
//				}
//			}
//
//		} catch (SQLException e) {
//			System.out.println("Error in initializing User object from ResultSet");
//			e.printStackTrace();
//		}
//	}

	/**
	 * @return the userid
	 */
	public int getUserid() {
		return userid;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the tickets
	 */
	public ArrayList<Ticket> getTickets() {
		return tickets;
	}
	
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	

}

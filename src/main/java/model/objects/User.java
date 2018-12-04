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
	 * 
	 */
	public User(ResultSet result) {
		try {
			if(result.next()) {
				this.userid = result.getInt(1);
				this.username = result.getString(2);
				this.tickets.add(new Ticket(result.getInt(3)));
				while(result.next()) {
					this.tickets.add(new Ticket(result.getInt(3)));
				}
			}

		} catch (SQLException e) {
			System.out.println("Error in initializing User object from ResultSet");
			e.printStackTrace();
		}
	}

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
	
	

}

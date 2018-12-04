/**
 * 
 */
package model.objects;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author anuragjha
 *
 */
public class Event {

	private int eventid;
	private String eventname;
	private int userid;
	private transient int numtickets;
	private int avail;
	private int purchased;


	public Event(int eventid, String eventname, int userid, int numtickets, int avail, int purchased) {

		this.eventid = eventid;
		this.eventname = eventname;
		this.userid = userid;
		this.numtickets = numtickets;
		this.avail = avail;
		this.purchased = purchased;	

	}


	/**
	 * 
	 */
	public Event(ResultSet result) {
		try {
			if(result.next()) {
				eventid = result.getInt(1);
				eventname = result.getString(2);
				userid = result.getInt(3);
				numtickets = result.getInt(4);
				avail = result.getInt(5);
				purchased = result.getInt(6);	
			}
		} catch (SQLException e) {
			System.out.println("Error in initializing Event object from ResultSet");
			e.printStackTrace();
		}
	}


	/**
	 * @return the eventid
	 */
	public int getEventid() {
		return eventid;
	}


	/**
	 * @return the eventname
	 */
	public String getEventname() {
		return eventname;
	}


	/**
	 * @return the userid
	 */
	public int getUserid() {
		return userid;
	}


	/**
	 * @return the numtickets
	 */
	public int getNumtickets() {
		return numtickets;
	}


	/**
	 * @return the avail
	 */
	public int getAvail() {
		return avail;
	}


	/**
	 * @return the purchased
	 */
	public int getPurchased() {
		return purchased;
	}




}

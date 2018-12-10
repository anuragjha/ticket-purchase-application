/**
 * 
 */
package model.objects;

/**
 * @author anuragjha
 *
 */
public class Ticket {

	private int eventid;

	/**
	 * 
	 */
	public Ticket(int eventid) {
		this.eventid = eventid;
		System.out.println("Event tickets for User : " + this.eventid);
	}
	
	/**
	 * @return the eventid
	 */
	public int getEventid() {
		return eventid;
	}

}

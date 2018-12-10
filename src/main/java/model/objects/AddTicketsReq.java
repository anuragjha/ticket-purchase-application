/**
 * 
 */
package model.objects;

/**
 * @author anuragjha
 *
 */
public class AddTicketsReq {

	private int eventid;
	private int tickets;
	
	/**
	 * 
	 */
	public AddTicketsReq(int eventid, int tickets) {
		this.eventid = eventid;
		this.tickets = tickets;
		
	}

}

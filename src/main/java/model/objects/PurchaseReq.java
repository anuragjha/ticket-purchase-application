/**
 * 
 */
package model.objects;

/**
 * @author anuragjha
 *
 */
public class PurchaseReq {

	int userid;
	int eventid;
	int tickets;
	
	
	/**
	 * constructor
	 */
	public PurchaseReq(int userid, int eventid, int tickets) {
		this.userid = userid;
		this.eventid = eventid;
		this.tickets = tickets;
	}

}

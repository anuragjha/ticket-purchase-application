package model.objects;

public class AppParams {
	
	private int userid;
	private int eventid;
	private String eventname;
	private int numtickets;
	private int tickets;
	private String username;
	private int targetuser;
	
	
	public AppParams() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the userid
	 */
	public int getUserid() {
		return userid;
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
	 * @return the numtickets
	 */
	public int getNumtickets() {
		return numtickets;
	}

	/**
	 * @return the tickets
	 */
	public int getTickets() {
		return tickets;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the targetuser
	 */
	public int getTargetuser() {
		return targetuser;
	}
	
	///////////////////

	/**
	 * @param userid the userid to set
	 */
	public void setUserid(int userid) {
		this.userid = userid;
	}

	/**
	 * @param eventid the eventid to set
	 */
	public void setEventid(int eventid) {
		this.eventid = eventid;
	}

	/**
	 * @param eventname the eventname to set
	 */
	public void setEventname(String eventname) {
		this.eventname = eventname;
	}

	/**
	 * @param numtickets the numtickets to set
	 */
	public void setNumtickets(int numtickets) {
		this.numtickets = numtickets;
	}

	/**
	 * @param tickets the tickets to set
	 */
	public void setTickets(int tickets) {
		this.tickets = tickets;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param targetuser the targetuser to set
	 */
	public void setTargetuser(int targetuser) {
		this.targetuser = targetuser;
	}
	
	
	

}

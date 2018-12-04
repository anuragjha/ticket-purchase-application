/**
 * 
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.google.gson.Gson;

/**
 * @author anuragjha
 *
 */
public class PreparedStmts {
	
	private Connection con;
	private PreparedStatement eventsTableAddEntry;
	private PreparedStatement eventsTableGetEventList;
	private PreparedStatement eventsTableGetEventDetails;
	private PreparedStatement transactionTableAddEntry;
	private PreparedStatement eventsTableUpdateForTickets;
	private PreparedStatement ticketsTableUpdateForTickets;
	private PreparedStatement userTableAddEntry;
	private PreparedStatement userTableGetUserDetails;
	private PreparedStatement ticketsTableGetEventidForUser;
	private PreparedStatement ticketsTableAddEntry;
	private PreparedStatement sqlLastInsertID;
	/**
	 * 
	 */
	public PreparedStmts(Connection con) {
		this.con = con;
		this.prepareAllStmts();
	}
	
	
	/**
	 * eventsTableAddEntry : adds a row to events table 
	 * @creates eventid
	 * @param eventname, userid, numtickets, avail, purchased
	 * @throws SQLException
	 * 
	 * @return the PreparedStatement eventsTableAddEntry
	 */
	public PreparedStatement getEventsTableAddEntry() {
		return eventsTableAddEntry;
	}


	/**
	 * eventsTableGetEventList : gets the event table - all rows
	 * @throws SQLException
	 * 
	 * @return the PreparedStatement eventsTableGetEventList
	 */
	public PreparedStatement getEventsTableGetEventList() {
		return eventsTableGetEventList;
	}
	

	/**
	 * eventsTableGetEventDetails : gets a ROW in the event table - 1 row
	 * @throws SQLException
	 * 
	 * @return the PreparedStatement eventsTableGetEventDetails
	 */
	public PreparedStatement getEventsTableGetEventDetails() {
		return eventsTableGetEventDetails;
	}


	/**
	 * transactionTableAddEntry : adds a row to transaction table
	 * @creates transactionid
	 * @param eventid, userid, numtickets, numtransfer, targetuserid 
	 * @throws SQLException
	 *
	 * @return the PreparedStatement transactionTableAddEntry
	 */
	public PreparedStatement getTransactionTableAddEntry() {
		return transactionTableAddEntry;
	}


	/**
	 * eventsTableUpdateForTickets : updates a row for a eventid
	 * @param @set avail, purchased
	 * @param @condition eventid
	 * @throws SQLException
	 * 
	 * @return the PreparedStatement eventsTableUpdateForTickets
	 */
	public PreparedStatement getEventsTableUpdateForTickets() {
		return eventsTableUpdateForTickets;
	}


	/**
	 * ticketsTableUpdateForTickets : updates a row in tickets table 
	 * @param @set numtickets
	 * @param @condition eventid, userid
	 * @throws SQLException
	 *
	 * @return the PreparedStatement ticketsTableUpdateForTickets
	 */
	public PreparedStatement getTicketsTableUpdateForTickets() {
		return ticketsTableUpdateForTickets;
	}


	/**
	 * userTableAddEntry : adds a row to user table
	 * @creates userid
	 * @param username
	 * @throws SQLException
	 *
	 * @return the PreparedStatement userTableAddEntry
	 */
	public PreparedStatement getUserTableAddEntry() {
		return userTableAddEntry;
	}


	/**
	 * userTableGetUserDetails : gets a ROW in the user table for a userid
	 * @param userid
	 * @throws SQLException
	 *
	 * @return the PreparedStatement userTableGetUserDetails
	 */
	public PreparedStatement getUserTableGetUserDetails() {
		return userTableGetUserDetails;
	}


	/**
	 * ticketsTableGetEventidForUser : gets a ROW in the tickets table for a userid
	 * @param userid
	 * @throws SQLException
	 *
	 * @return the PreparedStatement ticketsTableGetEventidForUser
	 */
	public PreparedStatement getTicketsTableGetEventidForUser() {
		return ticketsTableGetEventidForUser;
	}


	/**
	 * ticketsTableAddEntry : adds a row to tickets table
	 * @creates tickets
	 * @param eventid, userid, numtickets
	 * @throws SQLException
	 *
	 * @return the PreparedStatement ticketsTableAddEntry
	 */
	public PreparedStatement getTicketsTableAddEntry() {
		return ticketsTableAddEntry;
	}
	
	public PreparedStatement getLastInsertID() {
		return sqlLastInsertID;
	}

	/**
	 * prepareAllStmts method initializes all the sql statements
	 */
	private void prepareAllStmts() {
		try {
			
			this.lastInsertID();
			
			this.eventsTableAddEntry();
			this.eventsTableGetEventList();
			this.eventsTableGetEventDetails();
			this.eventsTableUpdateForTickets();
			
			this.ticketsTableAddEntry();
			this.ticketsTableUpdateForTickets(); //used in 1 case of transfer
			this.ticketsTableGetEventidForUser();

			this.userTableAddEntry();
			this.userTableGetUserDetails();
			
			this.transactionTableAddEntry();
			
		} catch (SQLException e) {
			System.out.println("Error in preparing sql stmts");
			e.printStackTrace();
		}
		
		
	}

	
	/*
	Event Service ->
	
	> POST /create	
	## after userID is checked in users table
	 insert into events(userID, eventName, numTickets)
	 	values(userid, eventname, numtickets);
*/
	/**
	 * eventsTableAddEntry : adds a row to events table 
	 * @creates eventid
	 * @param eventname, userid, numtickets, avail, purchased
	 * @throws SQLException
	 */
	private void eventsTableAddEntry() throws SQLException {
		String stmt = "insert into events(eventname, userid, numtickets, avail, purchased) "
				+ "values(?, ?, ?, ?, ?)";
		eventsTableAddEntry = con.prepareStatement(stmt);

	}
	
	
	//GET /list
	//select eventID, eventName, userID, avail, purchased from events;
	/**
	 * eventsTableGetEventList : gets the event table - all rows
	 * @throws SQLException
	 */
	private void eventsTableGetEventList() throws SQLException {
		String stmt = "select eventid, eventname, userid, numtickets, avail, purchased from events";
		eventsTableGetEventList = con.prepareStatement(stmt);
	}
	
	
	
/*	
	> GET /{eventid}
	 select eventID, eventName, userID, avail, purchased from events
	 	where eventID = eventid;
*/
	/**
	 * eventsTableGetEventDetails : gets a ROW in the event table - 1 row
	 * @throws SQLException
	 */
	private void eventsTableGetEventDetails() throws SQLException {
		String stmt = "select eventid, eventname, userid, numtickets, avail, purchased from events"
				+ " where eventid = ?";
		eventsTableGetEventDetails = con.prepareStatement(stmt);
	}
	
	
	/*
	> POST /purchase/{eventid}  
	## after all checks are passed ##
	 ## add a new row in transaction table
	 insert into transaction(eventID, userID, numTickets)
	 	values(eventid, userid, tickets);
	 ## alter events table and decrease "avail" for that event id
	 update events
	 	set avail = (avail - tickets), purchased = (purchased + tickets)
	 	where eventID = eventid;
	 ## add or update column for the particular "eventID" and "userID" in Tickets table
	  // if userID and eventID is not found in Tickets table - add a new row
	  insert into tickets(eventID, userID, numTickets)
	  	values(eventid, userid, tickets);
	  // if userID and eventID is found in Tickets table - update the row
	  update tickets
	  	set numTickets = numTickets + tickets
	  	where userID = userid and eventID = eventid;

	 */

	/**
	 * transactionTableAddEntry : adds a row to transaction table
	 * @creates transactionid
	 * @param eventid, userid, numtickets, numtransfer, targetuserid 
	 * @throws SQLException
	 */
	private void transactionTableAddEntry() throws SQLException {
		String stmt = "insert into transaction(eventid, userid, numtickets, numtransfer, targetuserid) "
				+ "values(?, ?, ?, ?, ?)";
		transactionTableAddEntry = con.prepareStatement(stmt);
	}
	
	/**
	 * eventsTableUpdateForTickets : updates a row for a eventid
	 * @param @set avail, purchased
	 * @param @condition eventid
	 * @throws SQLException
	 */
	private void eventsTableUpdateForTickets() throws SQLException {
		String stmt = "update events" +
		" set avail = ?, purchased = ?" +
		" where eventid = ?";
		eventsTableUpdateForTickets = con.prepareStatement(stmt);
	}
	
	
/// find below func for adding entry in tickets table
	
	/**
	 * ticketsTableUpdateForTickets : updates a row in tickets table 
	 * @param @set numtickets
	 * @param @condition eventid, userid
	 * @throws SQLException
	 */
	private void ticketsTableUpdateForTickets() throws SQLException {
		String stmt = "update tickets" +
		" set numtickets = ?" +
		" where eventid = ? and userid = ?";
		ticketsTableUpdateForTickets = con.prepareStatement(stmt);
	}
	
	
	
	/*
	 User Service ->
	> POST /create
	## check if username is already taken, if not then
	insert into users(userName) values(username);
	 // to return created user id
	 select userID from users where userName = username;
	*/
	/**
	 * userTableAddEntry : adds a row to user table
	 * @creates userid
	 * @param username
	 * @throws SQLException
	 */
	private void userTableAddEntry() throws SQLException {
		String stmt = "insert into users(username) "
				+ "values(?)";
		userTableAddEntry = con.prepareStatement(stmt);

	}
	
	
	/*
	> GET /{userid}
	## 
	select userID, userName from users
	where userID = userid
	right join on
	(Select * from tickets where userID = userid);
	*/
	/**
	 * userTableGetUserDetails : gets a ROW in the user table for a userid
	 * @param userid
	 * @throws SQLException
	 */
	private void userTableGetUserDetails() throws SQLException {
		String stmt = "select userid, username from users"
				+ " where userid = ?";
		userTableGetUserDetails = con.prepareStatement(stmt);
	}
	
	/**
	 * ticketsTableGetEventidForUser : gets a ROW in the tickets table for a userid
	 * @param userid
	 * @throws SQLException
	 */
	private void ticketsTableGetEventidForUser() throws SQLException {
		String stmt = "select eventid from tickets"
				+ " where userid = ?";
		ticketsTableGetEventidForUser = con.prepareStatement(stmt);
	}
	
	
	/*
	> POST /{userid}/tickets/add
	 // if for particular userid and eventid a row is present in tickets table then
	  	update tickets
	  		set numTickets = numTickets + tickets
	  		where userID = userid and eventID = eventid;
	*/
	
	/**
	 * ticketsTableAddEntry : adds a row to tickets table
	 * @creates tickets
	 * @param eventid, userid, numtickets
	 * @throws SQLException
	 */
	private void ticketsTableAddEntry() throws SQLException {
		String stmt = "insert into tickets(eventid, userid, numtickets) "
				+ "values(?, ?, ?)";
		ticketsTableAddEntry = con.prepareStatement(stmt);
		
	}
	
	
	private void lastInsertID() throws SQLException {
		String stmt = "SELECT LAST_INSERT_ID()";
		sqlLastInsertID = con.prepareStatement(stmt);
		
	}
	
	/*
	> POST /{userid}/tickets/transfer
	 // check if targetuser is present in user table
	 // check if for a particular userID and eventID a row exists in tickets table
	  // if exists, then is tickets <= numTickets of tickets table, if yes then
	 ## insert a new row in transaction table
	 insert into transaction(eventID, userID, numTickets, numTransfer, targetUserID)
	 	values(eventid, userid, 0, tickets, targetuser)
	 ## update tickets table first for user and then for targetUser
	 # for user -
	 	update tickets
	 		set numTickets = numTickets - tickets
	  		where userID = userid and eventID = eventid;
	# for targetuser
		# if targetUserID and eventID combo is not present
		insert into tickets(eventID, userID, numTickets)
	  		values(eventid, targetuser, tickets);
	  	# if targetUserID and eventID combo is present
	  	update tickets
	 		set numTickets = numTickets + tickets
	  		where userID = targetuser and eventID = eventid;
	 
	 */
	
	
	
	
	/*
	 Front End Service
	> GET /events
	call events api - GET /list
	
	> POST /events/create
	call events api - POST /create
	
	> GET /events/{eventid}
	call events api - GET /{eventid}
	
	> POST /events/{eventid}/purchase/{userid}
	call events api - POST /purchase/{eventid} 
	
	> POST /users/create
	call users api - POST /create
	
	> GET /users/{userid}
	call users api - GET /{userid}
	
	> POST /users/{userid}/tickets/transfer
	call users api - POST /users/{userid}/tickets/transfer
	
	
	 */
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

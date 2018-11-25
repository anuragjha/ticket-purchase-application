/**
 * 
 */
package model;

/**
 * @author anuragjha
 *
 */
public class PreparedStmt {
	
	/**
	 * 
	 */
	public PreparedStmt() {
		// TODO Auto-generated constructor stub
	}

	
	/*
	Event Service ->
	
	> POST /create	
	## after userID is checked in users table
	 insert into events(userID, eventName, numTickets)
	 	values(userid, eventname, numtickets);
	
	> GET /list
	 select eventID, eventName, userID, avail, purchased from events;
	
	> GET /{eventid}
	 select eventID, eventName, userID, avail, purchased from events
	 	where eventID = eventid;
	
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

	/*
	 User Service ->
	> POST /create
	## check if username is already taken, if not then
	insert into users(userName) values(username);
	 // to return created user id
	 select userID from users where userName = username;
	
	> GET /{userid}
	## 
	select userID, userName from users
	where userID = userid
	right join on
	(Select * from tickets where userID = userid);
	
	> POST /{userid}/tickets/add
	 // if for particular userid and eventid a row is present in tickets table then
	  	update tickets
	  		set numTickets = numTickets + tickets
	  		where userID = userid and eventID = eventid;
	
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

/**
 * 
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author anuragjha
 *
 */
public class DatabaseManager {

	private static final String username = "user20";
	private static final String password = "user20"; 
	private static final String db = "user20"; 
	private Connection con;// = DatabaseConnector.connectToDataBase(username, password, db);
	private PreparedStmts preparedStmts;// = new PreparedStmts(con);

	/**
	 * constructor
	 */
	public DatabaseManager() {
		con = DatabaseConnector.connectToDataBase(username, password, db);
		preparedStmts = new PreparedStmts(con);

	}


	/**
	 * @return the con
	 */
	public Connection getCon() {
		return con;
	}
	
	public void setAutoCommit(boolean value) {
		try {
			con.setAutoCommit(value);
			
		} catch(SQLException e) {
		System.out.println("Error in changing Auto Commit mode");
			e.printStackTrace();
		}
	}
	
	public void commit() {
		try {
			con.commit();
		} catch (SQLException e) {
			System.out.println("Error in commit");
			e.printStackTrace();
		}
	}
	
	
	public void rollback() {
		try {
			con.rollback();
		} catch (SQLException e) {
			System.out.println("Error in rollback");
			e.printStackTrace();
		}
	}

	/**
	 * @return the preparedstmts
	 */
	public PreparedStmts getPreparedstmts() {
		return preparedStmts;
	}

	public int eventsTableAddEntry(String eventname, int userid, int numtickets, 
			int avail, int purchased) {
		return this.getEventsTableAddEntry(eventname, userid, numtickets, avail, purchased);
	}


	public ResultSet eventsTableGetEventList() {
		return this.getEventsTableGetEventList();
	}

	public ResultSet eventsTableGetEventDetails(int eventid) {
		return this.getEventsTableGetEventDetails(eventid);
	}


	public int transactionsTableAddEntry(int eventid, int userid, int numtickets, int numtransfer, int targetuserid) {
		return this.getTransactionsTableAddEntry(eventid, userid, numtickets, numtransfer, targetuserid);
	}


	public int eventsTableUpdateForTickets(int avail, int purchased, int eventid) {
		return this.getEventsTableUpdateForTickets(avail, purchased, eventid);
	}


	public int ticketsTableUpdateForTickets(int numtickets, int eventid, int userid) {
		return this.getTicketsTableUpdateForTickets(numtickets, eventid, userid);
	}


	public int userTableAddEntry(String username) {
		return this.getUserTableAddEntry(username);
	}

	public String userTableGetEntry(int userid) {
		return this.getUserTableGetEntry(userid);
	}

	public int userTableIfUsernameExist(String username) {
		return this.getUserTableIfUsernameExist(username);
	}

//	public String ticketsTableGetUserDetails(int userid) {
//		return this.getTicketsTableGetUserDetails(userid);
//	}

	public ResultSet ticketsTableGetEventidForUser(int userid) {
		return this.getTicketsTableGetEventidForUser(userid);
	}


	public int ticketsTableAddEntry(int eventid, int userid, int numtickets) {
		return getTicketsTableAddEntry(eventid, userid, numtickets);
	}

	public int ticketsTableGetNoOfTickets(int userid, int eventid) {
		return getTicketsTableGetNoOfTickets(userid, eventid);
	}
	
	
	public boolean ticketsTableDeleteNoOfTickets(int userid, int eventid, int tickets) {
		return getTicketsTableDeleteNoOfTickets(userid, eventid, tickets);
	}

	///////////////////////TODO: dividing marker


	private synchronized int getEventsTableAddEntry(String eventname, int userid, int numtickets, 
			int avail, int purchased) {

		int eventid = 0;

		PreparedStatement sqlStmt = preparedStmts.getEventsTableAddEntry();
		try {
			sqlStmt.setString(1, eventname);
			sqlStmt.setInt(2, userid);
			sqlStmt.setInt(3, numtickets);
			sqlStmt.setInt(4, avail);
			sqlStmt.setInt(5, purchased);

			//boolean sqlStatus = sqlStmt.execute();
			//TODO://retrieving value
			if(sqlStmt.executeUpdate() > 0) {   //executing update
				ResultSet insertID = preparedStmts.getLastInsertID().executeQuery();
				if(insertID.next()) {
					eventid = insertID.getInt(1);
					System.out.println("last insert id: " + eventid);
					
				}
			}

			//this.getCon().close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return eventid;
	}


	private ResultSet getEventsTableGetEventList() {

		ResultSet result = null;

		PreparedStatement sqlStmt = preparedStmts.getEventsTableGetEventList();
		try {

			result = sqlStmt.executeQuery();

			//this.getCon().close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}



	private ResultSet getEventsTableGetEventDetails(int eventid) {
		ResultSet result = null;

		PreparedStatement sqlStmt = preparedStmts.getEventsTableGetEventDetails();
		try {

			sqlStmt.setInt(1, eventid);;

			result = sqlStmt.executeQuery();

			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}



	private synchronized int getTransactionsTableAddEntry(int eventid, int userid, int numtickets, int numtransfer,
			int targetuserid) {
		int transactionid = 0;

		PreparedStatement sqlStmt = preparedStmts.getTransactionsTableAddEntry();
		try {
			sqlStmt.setInt(1, eventid);
			sqlStmt.setInt(2, userid);
			sqlStmt.setInt(3, numtickets);
			sqlStmt.setInt(4, numtransfer);
			sqlStmt.setInt(5, targetuserid);

			//boolean sqlStatus = sqlStmt.execute();
			//TODO://retrieving value
			if(sqlStmt.executeUpdate() > 0) {   //executing update
				ResultSet insertID = preparedStmts.getLastInsertID().executeQuery();
				if(insertID.next()) {
					transactionid = insertID.getInt(1);
					System.out.println("last insert id: " + transactionid);
					
				}
			}

			//this.getCon().close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return transactionid;

	}


	private synchronized int getEventsTableUpdateForTickets(int avail, int purchased, int eventid) {

		int result = 0;
		PreparedStatement sqlStmt = preparedStmts.getEventsTableUpdateForTickets();
		try {
			sqlStmt.setInt(1, avail);
			sqlStmt.setInt(2, purchased);
			sqlStmt.setInt(3, eventid);

			//result = sqlStmt.executeQuery();
			result = sqlStmt.executeUpdate();
			//if( > 0) {   //executing update
				//ResultSet insertID = preparedStmts.getLastInsertID().executeQuery();
				//if(insertID.next()) {
				//	result = insertID.getInt(1);
				//	System.out.println("last insert id: " + result);	
				//}
			//}
			
			//this.getCon().close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}



	private synchronized int getTicketsTableUpdateForTickets(int numtickets, int eventid, int userid) {

		int result = 0;

		PreparedStatement sqlStmt = preparedStmts.getTicketsTableUpdateForTickets();
		try {
			sqlStmt.setInt(1, numtickets);
			sqlStmt.setInt(2, eventid);
			sqlStmt.setInt(3, userid);

			//result = sqlStmt.executeQuery();
			if(sqlStmt.executeUpdate() > 0) {   //executing update
				ResultSet insertID = preparedStmts.getLastInsertID().executeQuery();
				if(insertID.next()) {
					eventid = insertID.getInt(1);
					System.out.println("last insert id: " + eventid);
					
				}
			}
			//this.getCon().close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}


	private synchronized int getUserTableAddEntry(String username) {

		int userid = 0;

		PreparedStatement sqlStmt = preparedStmts.getUserTableAddEntry();
		try {
			sqlStmt.setString(1, username);

			//boolean sqlStatus = sqlStmt.execute();
			//TODO://retrieving value
			if(sqlStmt.executeUpdate() > 0) {   //executing update
				ResultSet insertID = preparedStmts.getLastInsertID().executeQuery();
				if(insertID.next()) {
					userid = insertID.getInt(1);
					System.out.println("last insert id: " + userid);
					
				}
			}

			//this.getCon().close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userid;
	}

	
	private String getUserTableGetEntry(int userid) {
		String username = "";

		PreparedStatement sqlStmt = preparedStmts.getUserTableGetEntry();
		try {
			System.out.println("getUserTableGetEntry : userid : " + userid);
			sqlStmt.setInt(1, userid);

			ResultSet result = sqlStmt.executeQuery();
			
			if(result.next()) {
				username = result.getString(1);
				System.out.println("username : " + username);
			}

			//this.getCon().close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return username;
	}
	
	
	private int getUserTableIfUsernameExist(String username) {
		int userid = 0;

		PreparedStatement sqlStmt = preparedStmts.getUserTableIfUsernameExist();
		try {
			System.out.println("getUserTableIfUsernameExist : username : " + username);
			sqlStmt.setString(1, username);

			ResultSet result = sqlStmt.executeQuery();
			
			if(result.next()) {
				userid = result.getInt(1);
				System.out.println("user : " + userid);
			}

			//this.getCon().close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userid;
	}

//	private String getTicketsTableGetUsername(int userid) {
//
//		String username = "";
//		ResultSet result = null;
//
//		PreparedStatement sqlStmt = preparedStmts.
//		try {
//
//			sqlStmt.setInt(1, userid);
//
//			result = sqlStmt.executeQuery();
//			if(result.next()) {
//				username = result.getString(1);
//			}
//
//			//this.getCon().close();
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return username;
//	}


	private ResultSet getTicketsTableGetEventidForUser(int userid) {
		ResultSet result = null;

		PreparedStatement sqlStmt = preparedStmts.getTicketsTableGetEventidForUser();
		try {

			sqlStmt.setInt(1, userid);

			result = sqlStmt.executeQuery();

			//this.getCon().close();

		} catch (SQLException e) {
			System.out.println("Error in getTicketsTableGetEventidForUser - DataBaseManager");
			e.printStackTrace();
		}

		return result;

	}
	
	
	private synchronized int getTicketsTableAddEntry(int eventid, int userid, int numtickets) {

		int ticketid = 0;

		PreparedStatement sqlStmt = preparedStmts.getTicketsTableAddEntry();
		try {
			sqlStmt.setInt(1, eventid);
			sqlStmt.setInt(2, userid);
			//sqlStmt.setInt(3, numtickets);
			
			//boolean sqlStatus = sqlStmt.execute();
			//TODO://retrieving value
			if(sqlStmt.executeUpdate() > 0) {   //executing update
				ResultSet insertID = preparedStmts.getLastInsertID().executeQuery();
				if(insertID.next()) {
					ticketid = insertID.getInt(1);
					System.out.println("last insert id: " + ticketid);
					
				}
			}

			//this.getCon().close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ticketid;
		
	}
	
	
	
	private int getTicketsTableGetNoOfTickets(int userid, int eventid) {
		int noOfTickets = 0;
		PreparedStatement sqlStmt = preparedStmts.getTicketsTableGetNoOfTickets();
		
		try {
			sqlStmt.setInt(1, userid);
			sqlStmt.setInt(2, eventid);
			
			ResultSet result = sqlStmt.executeQuery();
			if(result.next()) {
				noOfTickets = result.getInt(1);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return noOfTickets;
	}
	

	private boolean getTicketsTableDeleteNoOfTickets(int userid, int eventid, int tickets) {
		boolean deleted = false;
		PreparedStatement sqlStmt = preparedStmts.getTicketsTableDeleteNoOfTickets();
		
		try {
			sqlStmt.setInt(1, userid);
			sqlStmt.setInt(2, eventid);
			sqlStmt.setInt(3, tickets);
			
			int result = sqlStmt.executeUpdate();
			if(result > 0) {
				deleted = true;
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return deleted;
	}




	public void close() {
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println("Error in closing connection");
			e.printStackTrace();
		}
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		DatabaseManager dbm1 = new DatabaseManager();
		System.out.println("Connected to database");
		
		int id = dbm1.eventsTableAddEntry("testEvent1", 4, 15, 14, 1);
		System.out.println("id ::::::: " + id);

		ResultSet result = dbm1.eventsTableGetEventList();  //get event list
		try {
			while(result.next()) {
				System.out.println("result ::::::: " + result.getInt(1) + " " + result.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
//		id = dbm1.eventsTableAddEntry("testEvent13", 4, 15, 14, 1);
//		System.out.println("id ::::::: " + id);
//		
		dbm1.close();

		


	}













}

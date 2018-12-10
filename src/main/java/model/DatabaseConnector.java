/**
 * 
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import cs601.project4.AppConstants;

/**
 * @author anuragjha
 * DatabaseConnector loads database driver and connects to database
 */
public class DatabaseConnector {

	public static Connection connectToDataBase(String username, String password, String db) {

		try {
			// load driver
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		}
		catch (Exception e) {
			System.err.println("Can't find driver");
			System.exit(1);
		}
		System.out.println("jdbc driver loaded");

		//format "jdbc:mysql://[hostname][:port]/[dbname]"
		//note: if connecting through an ssh tunnel make sure to use 127.0.0.1 and
		//also to that the ports are set up correctly
		//////////String urlString = "jdbc:mysql://sql.cs.usfca.edu/"+db;
		/**
		 * ssh -L 3306:sql.cs.usfca.edu:3306 ajha6@stargate.cs.usfca.edu   // tunnel from project folder
		 */
		//////////String urlString = "jdbc:mysql://127.0.0.1:3306/"+db;
		String urlString = AppConstants.getInit().getDbHostname()+db;
		//Must set time zone explicitly in newer versions of mySQL.
		String timeZoneSettings = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

		Connection con = null;
		try {
			con = DriverManager.getConnection(urlString+timeZoneSettings, username, password);
		} catch (SQLException e) {
			System.out.println("Error in getting db connection");
			e.printStackTrace();
		}
		System.out.println("database connection made");
		
		
		return con;
	}

}

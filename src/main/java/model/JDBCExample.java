package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class JDBCExample {

	public static void main(String[] args) throws SQLException {
		String username  = "user20";
		String password  = "user20";
		String db  = "user20";

		try {
			// load driver
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		}
		catch (Exception e) {
			System.err.println("Can't find driver");
			System.exit(1);
		}

		// format "jdbc:mysql://[hostname][:port]/[dbname]"
		//note: if connecting through an ssh tunnel make sure to use 127.0.0.1 and
		//also to that the ports are set up correctly
		///////String urlString = "jdbc:mysql://sql.cs.usfca.edu:3306/"+db;
		/**
		 * ssh -L 3306:sql.cs.usfca.edu:3306 ajha6@stargate.cs.usfca.edu   // tunnel from project folder
		 */
		String urlString = "jdbc:mysql://127.0.0.1:3306/"+db;
		//Must set time zone explicitly in newer versions of mySQL.
		String timeZoneSettings = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";


		Connection con = DriverManager.getConnection(urlString+timeZoneSettings,
				username,
				password);

		//create a statement object
		Statement stmt = con.createStatement();

		//execute a query, which returns a ResultSet object
		ResultSet result = stmt.executeQuery (
				"SELECT * " + 
				"FROM Persons;");

		//iterate over the ResultSet
		while (result.next()) {
			//for each result, get the value of the columns name and id
			String nameres = result.getString("FirstName");
			int idres = result.getInt("PersonID");
			System.out.printf("name: %s id: %d\n", nameres, idres);
		}

		String name = "Robert";
		int id = 123;

		//reuse the statement to insert a new value into the table
		stmt.executeUpdate("INSERT INTO Persons (FirstName, PersonID) VALUES (\"" + name + "\", \"" + id + "\")");
		System.out.println("\n***\n");

		//print the updated table
		result = stmt.executeQuery (
				"SELECT * " + 
				"FROM Persons;");
		while (result.next()) {
			String nameres = result.getString("FirstName");
			int idres = result.getInt("PersonID");
			System.out.printf("name: %s id: %d\n", nameres, idres);
		}

		con.close();
	}
}

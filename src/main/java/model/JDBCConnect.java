/**
 * 
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author anuragjha
 *
 */
public class JDBCConnect {

	/**
	 * 
	 */
	public JDBCConnect() {
		// TODO Auto-generated constructor stub
	}





	public static void main(String[] args) {
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
		//////////String urlString = "jdbc:mysql://sql.cs.usfca.edu/"+db;
		String urlString = "jdbc:mysql://sql.cs.usfca.edu:3306/"+db;
		//Must set time zone explicitly in newer versions of mySQL.
		String timeZoneSettings = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		

		Connection con = null;
		try {
			con = DriverManager.getConnection(urlString+timeZoneSettings,
					username,
					password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//create a statement object
		Statement stmt = null;
		try {
			stmt = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//execute a query, which returns a ResultSet object
		ResultSet result = null;
		try {
			result = stmt.executeQuery (
					"SELECT * " + 
					"FROM Persons;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//iterate over the ResultSet
		try {
			while (result.next()) {
				//for each result, get the value of the columns name and id
				String nameres = result.getString("name");
				int idres = result.getInt("id");
				System.out.printf("name: %s id: %d\n", nameres, idres);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String name = "Robert";
		int id = 123;

		//reuse the statement to insert a new value into the table
		try {
			stmt.executeUpdate("INSERT INTO customers (name, id) VALUES (\"" + name + "\", \"" + id + "\")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("\n***\n");

		//print the updated table
		try {
			result = stmt.executeQuery (
					"SELECT * " + 
					"FROM persons;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while (result.next()) {
				String nameres = result.getString("name");
				int idres = result.getInt("id");
				System.out.printf("name: %s id: %d\n", nameres, idres);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

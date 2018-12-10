/**
 * 
 */
package service.user;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cs601.project4.Project4Logger;
import httpUtil.HttpRespUtil;
import model.DatabaseManager;
import model.objects.Event;
import model.objects.ResultEmpty;
import model.objects.User;

/**
 * @author anuragjha
 * UserDetailsHandler class handles request for User Details
 */
public class UserDetailsHandler {

	/**
	 * doGet handles request for user details
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String result = "";

		System.out.println("in doGet of UserDetailsServlet " + req.getRequestURI());

		String[] subPaths = req.getPathInfo().split("/");

		Project4Logger.write(Level.INFO, "Request : " + req.getRequestURI(), 1);
		
		if((subPaths.length == 2) && (subPaths[1].matches("[0-9]+")) && 
				(Integer.parseInt(subPaths[1]) > 0)) {
			result = this.getResult(resp, Integer.parseInt(subPaths[1]));

			if(result.equals("{\"error\":\"User not found\"}")) {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			} else {
				resp.setStatus(HttpServletResponse.SC_OK);
			}

			new HttpRespUtil().writeResponse(resp, result); 

		} else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}	
	}

	/**
	 * getResult implements the Events Detail functionality
	 * @param resp
	 * @param userid
	 * @return
	 */
	private synchronized String getResult(HttpServletResponse resp, int userid) {
		String resultJson  = "";

		DatabaseManager dbm1 = new DatabaseManager();
		System.out.println("Connected to database");

		String username = dbm1.userTableGetEntry(userid);

		resultJson = this.setResponse(resp, dbm1, userid, username);

		dbm1.close();

		return resultJson;
	}

	/**
	 * setResponse method create response body
	 * @param resp
	 * @param dbm1
	 * @param userid
	 * @param username
	 * @return
	 */
	private String setResponse(HttpServletResponse resp, DatabaseManager dbm1, int userid, String username) {
		if(username.equals("")) {
			System.out.println("User not found");
			ResultEmpty errorJson = new ResultEmpty("User not found");
			return new Gson().toJson(errorJson, ResultEmpty.class);
		} else {
			User user = new User(userid, username);
			ResultSet result = dbm1.ticketsTableGetEventidForUser(userid); 		  //get events for user
			user.setTickets(result);
			return new Gson().toJson(user, User.class);
		}
	}


}
